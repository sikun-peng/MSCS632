% Basic family facts

male(robert).
male(michael).
male(james).
male(david).
male(noah).
male(liam).

female(susan).
female(linda).
female(karen).
female(emma).
female(olivia).
female(sophia).

parent(robert, michael).
parent(susan, michael).
parent(robert, linda).
parent(susan, linda).
parent(robert, james).
parent(susan, james).

parent(michael, emma).
parent(karen, emma).
parent(michael, noah).
parent(karen, noah).

parent(linda, olivia).
parent(david, olivia).
parent(linda, liam).
parent(david, liam).

parent(james, sophia).
parent(emma, sophia).

% Derived relationships

child_of(Child, Parent) :-
    parent(Parent, Child).

grandparent_of(Grandparent, Grandchild) :-
    parent(Grandparent, Parent),
    parent(Parent, Grandchild).

sibling_of(X, Y) :-
    parent(Parent, X),
    parent(Parent, Y),
    X \= Y.

cousin_of(X, Y) :-
    parent(ParentX, X),
    parent(ParentY, Y),
    sibling_of(ParentX, ParentY),
    X \= Y.

descendant_of(Ancestor, Descendant) :-
    parent(Ancestor, Descendant).

descendant_of(Ancestor, Descendant) :-
    parent(Ancestor, Child),
    descendant_of(Child, Descendant).
