# Assignment 5 Report: Developing a Class-Based Ride Sharing System

**Student:** Your Name  
**Course:** MSCS-632-M30 Advanced Programming Languages  
**Term:** Summer 2026  
**GitHub Repository:** `https://github.com/your-username/your-assignment5-repo`

## Overview

This assignment implements a Ride Sharing System in C++ and Smalltalk using core object-oriented programming principles. In both versions, the design centers on a base `Ride` class and specialized ride subclasses that allow the system to model shared behavior while supporting ride-specific fare calculations. The overall goal is to show how object-oriented design improves modularity, reuse, and extensibility across two different programming languages.

## Encapsulation

Encapsulation is used in both implementations by keeping object state inside classes and exposing that state through clearly defined methods. In the C++ version, the `Driver` class stores `assignedRides` as a private vector, and the `Rider` class stores `requestedRides` privately as well. External code cannot modify these collections directly and must use methods such as `addRide()` and `requestRide()`. The base `Ride` class also protects ride data by storing ride identifiers, locations, and distance as private fields that are accessed through getter methods.

In the Smalltalk version, encapsulation is achieved through instance variables and message passing. The `Driver` and `Rider` objects manage their ride collections internally and expose behavior through methods such as `addRide:`, `requestRide:`, `getDriverInfo`, and `viewRides`. This approach keeps each object responsible for maintaining its own valid state and prevents unrelated code from manipulating internal data structures directly.

## Inheritance

Inheritance is demonstrated by defining `StandardRide` and `PremiumRide` as subclasses of `Ride` in both languages. The `Ride` class provides shared attributes such as `rideID`, `pickupLocation`, `dropoffLocation`, and `distance`, along with a general `rideDetails()` or `rideDetails` method for formatting ride information. This removes duplication because both ride types reuse the common data and behavior inherited from the base class.

The subclasses extend the base class by supplying their own fare logic. `StandardRide` calculates a lower-cost ride using a base fare and per-mile rate, while `PremiumRide` applies a higher base fare, higher mileage rate, and an extra service fee. This inheritance structure makes it easy to add future ride categories such as shared rides or luxury rides without redesigning the rest of the system.

## Polymorphism

Polymorphism appears when the program stores different ride objects in a single collection and then processes them through the same interface. In the C++ version, a `vector<shared_ptr<Ride>>` holds both `StandardRide` and `PremiumRide` objects. When the program calls `fare()` or `rideDetails()` on each item, the correct subclass method runs automatically. This is runtime polymorphism through virtual methods.

The Smalltalk version demonstrates the same principle through dynamic message dispatch. An `OrderedCollection` contains both `StandardRide` and `PremiumRide` objects, and the program sends the same messages, such as `fare` and `rideDetails`, to every ride in the collection. Each object responds according to its class. This shows that polymorphism can be expressed naturally in both a statically typed language and a purely object-oriented dynamic language.

## Conclusion

The Ride Sharing System demonstrates how encapsulation, inheritance, and polymorphism work together to build a maintainable object-oriented design. Encapsulation protects internal data, inheritance reduces code duplication, and polymorphism allows the program to handle different ride types through a unified interface. Although C++ and Smalltalk differ in syntax and type systems, both languages support the same core OOP ideas effectively.

## APA 7 Submission Notes

- Add a title page if your instructor requires one.
- Set the final paper to double spacing, 12-point Times New Roman, and 1-inch margins.
- Replace the GitHub repository placeholder with the real repository URL.
- Append screenshots of both program outputs after the discussion section.
