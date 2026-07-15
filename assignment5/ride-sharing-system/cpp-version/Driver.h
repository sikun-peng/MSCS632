#ifndef DRIVER_H
#define DRIVER_H

#include "Ride.h"

#include <string>
#include <vector>

class Driver {
public:
    Driver(std::string driverID, std::string name, double rating);

    void addRide(const RidePtr& ride);
    std::string getDriverInfo() const;
    double totalEarnings() const;

private:
    std::string driverID;
    std::string name;
    double rating;
    std::vector<RidePtr> assignedRides;
};

#endif
