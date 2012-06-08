(ns macros.util
  (use clojure.pprint clojure.walk))

(defn pprint-code [code]
  (with-pprint-dispatch code-dispatch
    (pprint code)))

(defn print-example [explanation code]
  (println "\n" explanation "\n")
  (pprint-code code))

(defn print-result [code]
  (println "Evaluates to:")
  (pprint (eval code)))

(defmacro example-nomacro
  ""
  [explanation code]
  `(let [code# '~code]
     (print-example ~explanation code#)
     (print-result code#)))

(defmacro example
  ""
  [explanation code]
  `(do
     (let [code# '~code
           expansion# (macroexpand-all code#)]
       (print-example ~explanation code#)
       (println "Expands to:")
       (pprint-code expansion#)
       (print-result code#))))
