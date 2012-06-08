(ns macros.quoting
  "")

;; single quotes turn a single expression into a literal
(list 'a 'b 'c)

(comment "doesn't work"
         (list a b c))
'(a b c)

;; back-quotes behave like single quotes (almost)
`(a b c)

;; they also allow for un-quoting
(let [name "John"]
  `(likes ~name beer))

;; tilde causes the evaluation of a symbol.
;; i.e. the symbol is expanded to its current value

;; ~@ is a 'splicing quote'. It unwraps one level of parens
(let [preference `(john beer)]
  `(likes ~@preference))

;; splicing quote also splices vectors and sets
(let [a '[john beer]
      b #{'matthew 'wine}]
  `(and (likes ~@a)
        (likes ~@b)))

;; splicing quote splices maps too, but not how you would expect
(let [preferences {'john 'beer 'matthew 'wine}]
  `(likes ~@preferences))
