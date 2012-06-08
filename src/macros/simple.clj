(ns macros.simple
  "macros are expanded at read time.
   the expansions are run at eval time.
   these 2 simple steps allow you to extend the language.")

(defmacro incr
  "a simple macro which we would never use in real code"
  [v]
  `(+ 1 ~v))


;; calling macroexpand-1 lets you see the expansion
(macroexpand-1 '(incr 1))

(incr 1)

(macroexpand '(let [x 2]
                (incr x)))

;; in emacs C-c C-m calls macroexpand

(defmacro if-nil
  "a slightly more useful example.
   notice how we're providing a feature that the language does not ship with.
   we're extending the language itself.
   how would you accomplish this in c, java, or even ruby?"
  [test then else]
  `(if (nil? ~test) ~then ~else))

(if-nil ()
        (println "nil")
        (println "not nil"))
