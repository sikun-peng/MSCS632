#ifndef RIDE_H
#define RIDE_H

#include <iomanip>
#include <memory>
#include <sstream>
#include <string>

class Ride {
public:
    Ride(
        std::string rideID,
        std::string pickupLocation,
        std::string dropoffLocation,
        double distance
    );
    virtual ~Ride() = default;

    const std::string& getRideID() const;
    const std::string& getPickupLocation() const;
    const std::string& getDropoffLocation() const;
    double getDistance() const;

    virtual double fare() const = 0;
    virtual std::string rideType() const = 0;
    virtual std::string rideDetails() const;

protected:
    std::string formatCurrency(double amount) const;

private:
    std::string rideID;
    std::string pickupLocation;
    std::string dropoffLocation;
    double distance;
};

using RidePtr = std::shared_ptr<Ride>;

#endif
