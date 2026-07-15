#ifndef PREMIUM_RIDE_H
#define PREMIUM_RIDE_H

#include "Ride.h"

class PremiumRide : public Ride {
public:
    PremiumRide(
        const std::string& rideID,
        const std::string& pickupLocation,
        const std::string& dropoffLocation,
        double distance
    );

    double fare() const override;
    std::string rideType() const override;

private:
    static constexpr double BASE_FARE = 5.00;
    static constexpr double RATE_PER_MILE = 3.25;
    static constexpr double SERVICE_FEE = 4.50;
};

#endif
