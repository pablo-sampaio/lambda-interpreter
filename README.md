
# Lambdac - Lambda-Calculus Interpreter

A simple Java interpreter for lambda expressions from the *untyped lambda calculus* formalism. 

Lambdac has been designed to be more syntatically friendly then the formal version.

As the formal lambda calculus is really an inspiration to the _functional programming_ paradigm, Lambdac can be seen 
as a low-level functional programming language.

## Features
- *Named expressions*: Lambdac allows one to define names (identifiers) associated to expressions, allowing the reuse of expressions
and allowing a modular definition of larger expressions. Each occurence of the name is replaced by the expression itself (like a macro 
in programming languages).
- The syntax for _applications_ is similar to function/procedure calls in many major programming languages, e.g.: f(x,y) instead of (f x y) or ((f x) y).
- *Named Constants* (or "atoms"), given as uppercase names. These constants need not to be previously defined and are not evaluated.
- C-style line comments starting with "//"
- Each program (lambda-expression) is divided in these two sections in this order: 
   1. DECLARATIONS, where named expressions are defined.
   2. BODY, where the main expression to be evaluated (possibly using the definitions) is given.
- A simple visual editor


## Code example

```
DECLARATIONS

	zero  := \lambda f, x . x 
	succ  := \lambda num, f, x . f(num(f,x))
	pred  := \lambda num, f, x . num (\lambda g, h. h(g(f))) (\lambda u . x) (\lambda u. u) 
	
	one   := succ(zero)
	two   := succ(one)
	three := succ(two)
	
	add  := \lambda n, m, f, x . n( f, m(f,x) )
	sub  := \lambda n, m . m(pred,n)
	mult := \lambda n, m, f . n(m(f))

	true  := \lambda x, y . x
	false := \lambda x, y . y
	if-then-else := \lambda a, b, c. a(b,c)
	
	is-zero := \lambda num . num((\lambda z . false), true)
	less-eq := \lambda n, m . is-zero(sub(n,m))

	y-combinator := \lambda g . (\lambda x . g(x(x))) (\lambda y . g(y(y)))

	factorial := y-combinator
	(  \lambda fact, n . 
		if-then-else (  is-zero(n)  ) 
			(  one  )
			(  mult(n,fact(pred(n)))  )
	)

BODY

	factorial(succ(succ(three)))

```

## Java Project

Folder "Lambdac" includes a Eclipse Java project with the interpreter. 

Inside the project, open the visual editor, running one of these classes: "lambdac.Interpreter" or "lambdac.Main".

See Lambdac code examples in folder "examples/".
- There are Lambdac implementations of natural (Church) numbers, operations, pairs, lists, booleans, 
if-then-else
- An also: recursive functions (with Y-combinator), fibonacci, insertion-sort, merge-sort and quick-sort.
- The examples are numbered in a didactic order. The last examples are more complex in terms of code readability and in time of execution. 
- Some of them (like quicksort) can only run to completion with really small inputs.


## Other Comments

This project was created around 2014 as a side project (almost as a hobby) to practice compiler/interpreter building techniques 
and to play a little bit with lambda calculus.

The expression evaluation works correctly (as far as I know), but is inherently inefficient and may give some heap or stack overflow in larger expressions. 
It does not follow a specific formally-defined evaluation scheme precisely. It was initially implemented "by intuition", based on typical programming languages
interpreters, then I have changed it some times to try to improve efficiency, trying some heuristic improvements as well.


## Reference

The wikipedia article is a good introduction to lambda calculus:
https://en.wikipedia.org/wiki/Lambda_calculus
