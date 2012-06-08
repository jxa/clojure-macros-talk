(ns macros.when-not
  "

Rule of thumb:
Don't write a macro when a function is sufficient.

Macro Advantages
 - Embedded languages
 - Computation at compile time
 - Could save function calls when performance is critical

Macro Disadvantages
 - functions can be passed as arguments. macros cannot
 - functions can be returned by functions. macros cannot
 - implementations are generally harder to read

")
