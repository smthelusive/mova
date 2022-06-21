# Mova

Mova is a PoC of a small (but proud) very human-like programming language.\
Being built for educational & fun purposes.

The name is inspired by Ukraine. "Mova" in Ukrainian means "language".

### Planned stack:
- Java
- ANTLR
- ASM
- Spock

## Mova documentation
### Types
Mova supports 3 data types: String, Decimal and Integer.

String values can be taken into double quotes:\
"hello Mova"\
the quotes can be omitted if there are no spaces in the string and if there is no variable defined with such name:\
helloMova

Decimal types contain coma:\
3,14\
Integer types contain only numeric values:\
42


### Syntax
Every statement in Mova is a "sentence" hence it should be concluded with a period.

Mova allows many forms of writing the same thing. To define a variable there are ways:\
a is 1.\
a equals 1.\
a = 1.\
... try other formulations!

For arithmetic operations you can use classical way:\
b = 1 + (5 * 4) - 18 / 5.\
as well as literals:\
b is 5 plus 3 minus 15.

string is "world" prefixed with "hello" suffix "!".

increment a.\
b is a decrement.\
a++.\
++b.\
--a.\
--b.

To print anything in the console, use 'print' or 'show' or 'output':\
show a.\
print b.

### Conditional block examples:
a is 5.\
if a < 10 or a > 15 a increment also show a otherwise show "something else".

"also" is used to split different commands within one block, for example,
within conditional block or a loop body. You can nest as many loops and
other valid structures as you wish. It will all be processed recursively
until the end of the sentence designated with '.'

### Loop examples:
repeat a++ also show a until a is 100.

do show a 5 times.

repeat 5 times:\
if a < 10 a increment also show a.

### Comment examples:
note: this is a comment\
comment: this is also a comment\
// yet another comment

### Program arguments:
to access program argument n, simply use argn:\
a is arg0.\
show arg1 + arg2.

The type of the argument originally is string, but later it 
depends on it's use. If arithmetic operations
are applied, such as plus, minus, multiply and divide, the value will be
treated as decimal value. If it can't be converted to decimal because
of syntax flaws, the program will fail.
