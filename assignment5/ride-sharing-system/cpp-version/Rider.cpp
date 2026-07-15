#include "Rider.h"

#include <sstream>

Rider::Rider(std::string riderIDValue, std::string nameValue)
    : riderID(std::move(riderIDValue)),
      name(std::move(nameValue)) {}

void Rider::requestRide(const RidePtr& ride) {
    requestedRides.push_back(ride);
}

std::string Rider::viewRides() const {
    std::ostringstream output;
    output << "Rider ID: " << riderID << ", Name: " << name << '\n';
    output << "Ride History:" << '\n';

    for (const RidePtr& ride : requestedRides) {
        output << "  - " << ride->rideDetails() << '\n';
    }

    return output.str();
}
