#ifndef RIDER_H
#define RIDER_H

#include "Ride.h"

#include <string>
#include <vector>

class Rider {
public:
    Rider(std::string riderID, std::string name);

    void requestRide(const RidePtr& ride);
    std::string viewRides() const;

private:
    std::string riderID;
    std::string name;
    std::vector<RidePtr> requestedRides;
};

#endif
