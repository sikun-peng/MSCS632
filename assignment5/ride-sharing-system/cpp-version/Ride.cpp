#include "Ride.h"

Ride::Ride(
    std::string rideIDValue,
    std::string pickupLocationValue,
    std::string dropoffLocationValue,
    double distanceValue
) : rideID(std::move(rideIDValue)),
    pickupLocation(std::move(pickupLocationValue)),
    dropoffLocation(std::move(dropoffLocationValue)),
    distance(distanceValue) {}

const std::string& Ride::getRideID() const {
    return rideID;
}

const std::string& Ride::getPickupLocation() const {
    return pickupLocation;
}

const std::string& Ride::getDropoffLocation() const {
    return dropoffLocation;
}

double Ride::getDistance() const {
    return distance;
}

std::string Ride::rideDetails() const {
    std::ostringstream output;
    output << "[" << rideType() << "] "
           << "Ride ID: " << rideID
           << ", Pickup: " << pickupLocation
           << ", Dropoff: " << dropoffLocation
           << ", Distance: " << std::fixed << std::setprecision(1) << distance << " miles"
           << ", Fare: " << formatCurrency(fare());
    return output.str();
}

std::string Ride::formatCurrency(double amount) const {
    std::ostringstream output;
    output << "$" << std::fixed << std::setprecision(2) << amount;
    return output.str();
}
