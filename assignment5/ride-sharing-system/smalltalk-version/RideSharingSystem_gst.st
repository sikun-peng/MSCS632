Object subclass: Ride [
    | rideID pickupLocation dropoffLocation distance |

    Ride class >> newRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance [
        ^self new
            initializeRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance;
            yourself
    ]

    initializeRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance [
        rideID := aRideID.
        pickupLocation := aPickup.
        dropoffLocation := aDropoff.
        distance := aDistance.
    ]

    rideID [
        ^rideID
    ]

    pickupLocation [
        ^pickupLocation
    ]

    dropoffLocation [
        ^dropoffLocation
    ]

    distance [
        ^distance
    ]

    fare [
        self subclassResponsibility
    ]

    rideType [
        self subclassResponsibility
    ]

    formatNumber: aNumber decimals: decimalPlaces [
        | scale scaled whole fraction fractionText |
        scale := 10 raisedTo: decimalPlaces.
        scaled := ((aNumber * scale) + 0.5) floor.
        whole := scaled // scale.
        fraction := scaled \\ scale.
        fractionText := fraction printString.

        [ fractionText size < decimalPlaces ] whileTrue: [
            fractionText := '0', fractionText
        ].

        ^whole printString, '.', fractionText
    ]

    rideDetails [
        ^String streamContents: [ :stream |
            stream
                nextPutAll: '[' , self rideType , '] ';
                nextPutAll: 'Ride ID: ' , rideID;
                nextPutAll: ', Pickup: ' , pickupLocation;
                nextPutAll: ', Dropoff: ' , dropoffLocation;
                nextPutAll: ', Distance: ' , (self formatNumber: distance decimals: 1) , ' miles';
                nextPutAll: ', Fare: $' , (self formatNumber: self fare decimals: 2)
        ]
    ]
]

Ride subclass: StandardRide [
    StandardRide class >> newRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance [
        ^self new
            initializeRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance;
            yourself
    ]

    fare [
        ^2.5 + (self distance * 1.75)
    ]

    rideType [
        ^'Standard'
    ]
]

Ride subclass: PremiumRide [
    PremiumRide class >> newRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance [
        ^self new
            initializeRideID: aRideID pickup: aPickup dropoff: aDropoff distance: aDistance;
            yourself
    ]

    fare [
        ^5.0 + 4.5 + (self distance * 3.25)
    ]

    rideType [
        ^'Premium'
    ]
]

Object subclass: Driver [
    | driverID name rating assignedRides |

    Driver class >> newDriverID: anID name: aName rating: aRating [
        ^self new
            initializeDriverID: anID name: aName rating: aRating;
            yourself
    ]

    initializeDriverID: anID name: aName rating: aRating [
        driverID := anID.
        name := aName.
        rating := aRating.
        assignedRides := OrderedCollection new.
    ]

    addRide: aRide [
        assignedRides add: aRide
    ]

    totalEarnings [
        ^assignedRides inject: 0 into: [ :sum :ride | sum + ride fare ]
    ]

    getDriverInfo [
        ^String streamContents: [ :stream |
            stream
                nextPutAll: 'Driver ID: ' , driverID;
                nextPutAll: ', Name: ' , name;
                nextPutAll: ', Rating: ' , (Ride new formatNumber: rating decimals: 1);
                nextPutAll: ', Completed Rides: ' , assignedRides size printString;
                nextPutAll: ', Earnings: $' , (Ride new formatNumber: self totalEarnings decimals: 2)
        ]
    ]
]

Object subclass: Rider [
    | riderID name requestedRides |

    Rider class >> newRiderID: anID name: aName [
        ^self new
            initializeRiderID: anID name: aName;
            yourself
    ]

    initializeRiderID: anID name: aName [
        riderID := anID.
        name := aName.
        requestedRides := OrderedCollection new.
    ]

    requestRide: aRide [
        requestedRides add: aRide
    ]

    viewRides [
        ^String streamContents: [ :stream |
            stream
                nextPutAll: 'Rider ID: ' , riderID , ', Name: ' , name; nl;
                nextPutAll: 'Ride History:'; nl.
            requestedRides do: [ :ride |
                stream nextPutAll: '  - ' , ride rideDetails; nl
            ]
        ]
    ]
]

Object subclass: RideSharingDemo [
    RideSharingDemo class >> run [
        | rider driver rides |

        rider := Rider newRiderID: 'R201' name: 'Ava Thompson'.
        driver := Driver newDriverID: 'D101' name: 'Marcus Lee' rating: 4.9.

        rides := OrderedCollection new.
        rides add: (StandardRide newRideID: 'SR001' pickup: 'Campus Library' dropoff: 'Airport' distance: 8.2).
        rides add: (PremiumRide newRideID: 'PR002' pickup: 'Downtown Hotel' dropoff: 'Tech Park' distance: 12.5).
        rides add: (StandardRide newRideID: 'SR003' pickup: 'Museum District' dropoff: 'River Walk' distance: 5.4).

        Transcript show: 'Ride Sharing System Demo'; nl.
        Transcript show: '========================'; nl; nl.
        Transcript show: 'Polymorphic Fare Calculation'; nl.

        rides do: [ :ride |
            rider requestRide: ride.
            driver addRide: ride.
            Transcript show: ride rideDetails; nl
        ].

        Transcript nl.
        Transcript show: rider viewRides.
        Transcript show: driver getDriverInfo; nl.
    ]
]

RideSharingDemo run
