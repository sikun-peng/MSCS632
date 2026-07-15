# Assignment 8 Report: Building a Family Tree in Prolog

**Student:** Your Name  
**Course:** MSCS-632-M30 Advanced Programming Languages  
**Term:** Summer 2026  
**GitHub Repository:** `https://github.com/your-username/your-assignment8-repo`

This assignment implements a family tree in Prolog by combining simple facts with logical rules that allow the program to infer more complex relationships. The basic facts define family members through `male`, `female`, and `parent` predicates. From these facts, derived rules were created for `child_of`, `grandparent_of`, `sibling_of`, `cousin_of`, and `descendant_of`. The main advantage of Prolog is that the programmer focuses on describing relationships rather than writing step-by-step control flow. Once the facts and rules are defined, Prolog can answer queries through pattern matching and logical inference. This makes the language especially well suited for relationship-based problems such as family trees.

One of the main challenges in this assignment is writing rules that are correct without producing unintended matches. For example, the `sibling_of` rule must ensure that a person is not considered their own sibling, and the `cousin_of` rule must build on parent and sibling relationships carefully. Recursion is especially important in the `descendant_of` rule, where the program must handle both direct children and deeper generations. Recursive logic is powerful, but it also requires attention because poorly designed rules can lead to repeated or confusing query results. Overall, the assignment demonstrates how Prolog approaches problem solving through declarative logic. Instead of telling the program how to search through the family tree step by step, the programmer defines facts and inference rules, and Prolog determines the answers automatically.
