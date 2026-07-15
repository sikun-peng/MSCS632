# Ride Sharing System

This project implements a class-based Ride Sharing System in two languages:

- C++
- Smalltalk

The assignment demonstrates three object-oriented programming principles:

- encapsulation
- inheritance
- polymorphism

## Project Structure

```text
ride-sharing-system/
├── README.md
├── cpp-version/
│   ├── Driver.cpp
│   ├── Driver.h
│   ├── PremiumRide.cpp
│   ├── PremiumRide.h
│   ├── Ride.cpp
│   ├── Ride.h
│   ├── Rider.cpp
│   ├── Rider.h
│   ├── StandardRide.cpp
│   ├── StandardRide.h
│   └── main.cpp
├── report/
│   └── assignment5_report.md
├── screenshots/
└── smalltalk-version/
    └── RideSharingSystem.st
```

## Features

- `Ride` base class with shared ride data
- `StandardRide` and `PremiumRide` subclasses that override `fare()`
- `Driver` class with private assigned ride history
- `Rider` class with private requested ride history
- polymorphic processing of mixed ride types in a single collection

## How to Run the C++ Version

From the project root:

```bash
cd cpp-version
g++ -std=c++17 -Wall -Wextra -pedantic main.cpp Ride.cpp StandardRide.cpp PremiumRide.cpp Driver.cpp Rider.cpp -o ride_sharing
./ride_sharing
```

## How to Run the Smalltalk Version

1. Open a Smalltalk environment such as Pharo or Squeak.
2. File in `smalltalk-version/RideSharingSystem.st`.
3. Run the following expression in a workspace:

```smalltalk
RideSharingDemo run
```

## Sample Screenshot Guidance

Add screenshots before submission:

- `screenshots/cpp-output.png`
- `screenshots/smalltalk-output.png`

## Report Note

The report draft is included in `report/assignment5_report.md`. Replace the GitHub repository placeholder with your actual repository URL and append the required screenshots when preparing the final submission.
