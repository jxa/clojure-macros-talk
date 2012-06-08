(ns macros.types
  "commonly used types of macros")

;; Creating Context


(defmacro with-file
  "simplified and adapted from with-open"
  [name file & body]
  `(let [~name (clojure.java.io/reader ~file)]
     (try
       ~@body
       (finally (.close ~name)))))

(with-file f "src/macros/types.clj"
  (println (slurp f)))


(defmacro cond-let
  "conditionally set let bindings.
   this macro uses recursion."
  [clauses & body]
  (when clauses
    `(if ~(first clauses)
       (let ~(second clauses)
         ~@body)
       (cond-let ~(next (next clauses)) ~@body))))

(cond-let [(< 2 1) [a 1 b 2]
           (= 2 1) [a 2 b 2]
           :else   [a 3 b 3]]
          (println "a:" a "b:" b))

(let [foo ['a 3 'b 3]]
  (cond-let [(< 2 1) [a 1 b 2]
             (= 2 1) [a 2 b 2]
             :else   foo]
            (println "a:" a "b:" b)))


;; Conditional Evaluation

(defmacro if-nil
  "Macros can be useful to prevent execution of the arguments.
   Functions always evaluate all arguments before calling the fn.
   Macros let you decide if and when to evaluate."
  [test then else]
  `(if (nil? ~test) ~then ~else))

(if-nil ()
        (println "nil")
        (println "not nil"))

(defn if-is-nil
  "if-nil is much cleaner when written as a macro.
   if it were a function, we would need to pass functions
   to prevent argument evaluation"
  [test then else]
  (if (nil? test)
    (then)
    (else)))

(if-is-nil ()
           (fn [] (println "nil"))
           (fn [] (println "not nil")))


;; Multiple Evaluation / Iteration

(defn deref-exp [name expression]
  (if (seq? expression)
    (map #(deref-exp name %) expression)
    (if (= name expression)
      `(deref ~name)
      expression)))

(defmacro imperative-for
  "a for loop which might look familiar to imperative programmers.
   execute the body until test returns false."
  [name init test incr & body]
  `(let [~name (atom ~init)]
     (while ~(deref-exp name test)
       (let [result# ~@(deref-exp name body)]
         (swap! ~name ~incr)
         result#))))

(imperative-for i 0 (< i 10) inc
                (println i))

(imperative-for i 10 (> i 0) dec
                (println i))


;; Argument destructuring / manipulation


;; Computing things at compile time

(defmacro average
  "this will compute the average and insert the result.
   just for illustration purposes."
  [& nums]
  (/ (apply + nums) (count nums)))

(average 1 2 3 4 5 56 67 8 9 0)
(average (range 10))

;; TODO On Lisp pg 187: Bezier curves

;; Hiding complex interfaces

(defmacro with-scope [scope & body]
  `(let [context# (Context/enter)
         ~scope (.scope context#)]
     (.setOptimizationLevel context# -1)
     (try ~@body
          (finally (Context/exit)))))

(with-scope s (.putObject "foo" "bar"))
