# Assignment 7 Report: Multi-Paradigm Problem Solving

**Student:** Your Name  
**Course:** MSCS-632-M30 Advanced Programming Languages  
**Term:** Summer 2026  
**GitHub Repository:** `https://github.com/your-username/your-assignment7-repo`

This assignment compares three different programming paradigms by solving the same statistics problem in C, OCaml, and Python. In each implementation, the program calculates the mean, median, and mode for a list of integers, but the programming style changes significantly depending on the language. The C version follows a procedural approach by organizing the solution as a sequence of functions that operate directly on arrays. This makes the control flow explicit and gives the programmer direct responsibility for low-level details such as copying arrays, sorting data, and managing dynamically allocated memory. The procedural style is straightforward and efficient, but it requires more manual work and careful attention to memory handling.

The OCaml and Python versions highlight two very different alternatives. The OCaml implementation uses a functional style with immutable lists, higher-order functions, and expression-based logic to compute the same statistics without relying on mutable state. This makes the code concise and mathematically clear, although some operations, such as counting frequencies, require more abstract thinking than in C. The Python version uses an object-oriented approach by defining a `StatisticsCalculator` class with separate methods for mean, median, and mode. This style groups related data and behavior together, making the program easy to read, extend, and reuse. Overall, the assignment shows that procedural programming emphasizes step-by-step control, functional programming emphasizes immutable transformations, and object-oriented programming emphasizes encapsulation and organized behavior around objects.
