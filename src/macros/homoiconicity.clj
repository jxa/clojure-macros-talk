(ns macros.homoiconicity

  "Clojure is a lisp
   Lisps are homoiconic languages
   programs are represented as primitive data structures"

  (:use [macros.util :only [example example-nomacro]]))

;; (Read)EPL
;; read-string can be used to demonstrate the Read step of REPL
(read-string "(foo)")

;; read-string returns normal clojure data structures
(let [data (read-string "(foo bar baz)")]
  (nth data 1))

;; Read + Eval
(let [prog (read-string "(println \"Hello\" \"REPL\")")]
  (eval prog))


(example-nomacro "read-string can be used to demonstrate the Read step of REPL"
  (read-string "(foo)"))

(example-nomacro "R(Eval)PL"
  (let [prog (list 'str "hello" :eval)]
    (eval prog)))

(example-nomacro "Read + Eval"
  (let [prog (read-string "(println \"Hello\" \"REPL\")")]
    (eval prog)))
