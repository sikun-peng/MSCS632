#ifndef STANDARD_RIDE_H
#define STANDARD_RIDE_H

#include "Ride.h"

class StandardRide : public Ride {
public:
    StandardRide(
        const std::string& rideID,
        const std::string& pickupLocation,
        const std::string& dropoffLocation,
        double distance
    );

    double fare() const override;
    std::string rideType() const override;

private:
    static constexpr double BASE_FARE = 2.50;
    static constexpr double RATE_PER_MILE = 1.75;
};

#endif
