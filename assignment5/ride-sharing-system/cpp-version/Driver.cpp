#include "Driver.h"

#include <iomanip>
#include <sstream>

Driver::Driver(std::string driverIDValue, std::string nameValue, double ratingValue)
    : driverID(std::move(driverIDValue)),
      name(std::move(nameValue)),
      rating(ratingValue) {}

void Driver::addRide(const RidePtr& ride) {
    assignedRides.push_back(ride);
}

std::string Driver::getDriverInfo() const {
    std::ostringstream output;
    output << "Driver ID: " << driverID
           << ", Name: " << name
           << ", Rating: " << std::fixed << std::setprecision(1) << rating
           << ", Completed Rides: " << assignedRides.size()
           << ", Earnings: $" << std::fixed << std::setprecision(2) << totalEarnings();
    return output.str();
}

double Driver::totalEarnings() const {
    double total = 0.0;
    for (const RidePtr& ride : assignedRides) {
        total += ride->fare();
    }
    return total;
}
