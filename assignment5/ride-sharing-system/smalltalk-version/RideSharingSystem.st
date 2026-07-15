"Assignment 5 - Ride Sharing System in Smalltalk"

Object subclass: #Ride
    instanceVariableNames: 'rideID pickupLocation dropoffLocation distance'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

Ride >> initializeRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance
    rideID := aRideID.
    pickupLocation := aPickup.
    dropoffLocation := aDropoff.
    distance := aDistance!

Ride >> rideID
    ^ rideID!

Ride >> pickupLocation
    ^ pickupLocation!

Ride >> dropoffLocation
    ^ dropoffLocation!

Ride >> distance
    ^ distance!

Ride >> fare
    self subclassResponsibility!

Ride >> rideType
    self subclassResponsibility!

Ride >> rideDetails
    ^ String streamContents: [ :stream |
        stream
            nextPutAll: '[' , self rideType , '] ';
            nextPutAll: 'Ride ID: ' , rideID;
            nextPutAll: ', Pickup: ' , pickupLocation;
            nextPutAll: ', Dropoff: ' , dropoffLocation;
            nextPutAll: ', Distance: ' , distance asString , ' miles';
            nextPutAll: ', Fare: $' , (((self fare * 100) rounded / 100) asString) ]!

Ride subclass: #StandardRide
    instanceVariableNames: ''
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

StandardRide >> fare
    ^ 2.5 + (distance * 1.75)!

StandardRide >> rideType
    ^ 'Standard'!

Ride subclass: #PremiumRide
    instanceVariableNames: ''
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

PremiumRide >> fare
    ^ 5.0 + 4.5 + (distance * 3.25)!

PremiumRide >> rideType
    ^ 'Premium'!

Object subclass: #Driver
    instanceVariableNames: 'driverID name rating assignedRides'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

Driver >> initializeDriverID: anID name: aName rating: aRating
    driverID := anID.
    name := aName.
    rating := aRating.
    assignedRides := OrderedCollection new!

Driver >> addRide: aRide
    assignedRides add: aRide!

Driver >> totalEarnings
    ^ assignedRides inject: 0 into: [ :sum :ride | sum + ride fare ]!

Driver >> getDriverInfo
    ^ String streamContents: [ :stream |
        stream
            nextPutAll: 'Driver ID: ' , driverID;
            nextPutAll: ', Name: ' , name;
            nextPutAll: ', Rating: ' , rating asString;
            nextPutAll: ', Completed Rides: ' , assignedRides size asString;
            nextPutAll: ', Earnings: $' , ((((self totalEarnings) * 100) rounded / 100) asString) ]!

Object subclass: #Rider
    instanceVariableNames: 'riderID name requestedRides'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

Rider >> initializeRiderID: anID name: aName
    riderID := anID.
    name := aName.
    requestedRides := OrderedCollection new!

Rider >> requestRide: aRide
    requestedRides add: aRide!

Rider >> viewRides
    ^ String streamContents: [ :stream |
        stream
            nextPutAll: 'Rider ID: ' , riderID , ', Name: ' , name; cr;
            nextPutAll: 'Ride History:'; cr.
        requestedRides do: [ :ride |
            stream nextPutAll: '  - ' , ride rideDetails; cr ] ]!

Object subclass: #RideSharingDemo
    instanceVariableNames: ''
    classVariableNames: ''
    poolDictionaries: ''
    category: 'RideSharingSystem'!

RideSharingDemo class >> run
    | rider driver rides |
    rider := Rider new.
    rider initializeRiderID: 'R201' name: 'Ava Thompson'.

    driver := Driver new.
    driver initializeDriverID: 'D101' name: 'Marcus Lee' rating: 4.9.

    rides := OrderedCollection new.

    rides add: (StandardRide new
        initializeRideID: 'SR001'
        pickup: 'Campus Library'
        dropoff: 'Airport'
        distance: 8.2;
        yourself).

    rides add: (PremiumRide new
        initializeRideID: 'PR002'
        pickup: 'Downtown Hotel'
        dropoff: 'Tech Park'
        distance: 12.5;
        yourself).

    rides add: (StandardRide new
        initializeRideID: 'SR003'
        pickup: 'Museum District'
        dropoff: 'River Walk'
        distance: 5.4;
        yourself).

    Transcript clear.
    Transcript show: 'Ride Sharing System Demo'; cr.
    Transcript show: '========================'; cr; cr.
    Transcript show: 'Polymorphic Fare Calculation'; cr.

    rides do: [ :ride |
        rider requestRide: ride.
        driver addRide: ride.
        Transcript show: ride rideDetails; cr ].

    Transcript cr.
    Transcript show: rider viewRides; cr.
    Transcript show: driver getDriverInfo; cr!

"Evaluate this in a workspace after filing in the classes:"
"RideSharingDemo run"
