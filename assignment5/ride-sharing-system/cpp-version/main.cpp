#include "Driver.h"
#include "PremiumRide.h"
#include "Rider.h"
#include "StandardRide.h"

#include <iostream>
#include <vector>

int main() {
    Rider rider("R201", "Ava Thompson");
    Driver driver("D101", "Marcus Lee", 4.9);

    std::vector<RidePtr> rides = {
        std::make_shared<StandardRide>("SR001", "Campus Library", "Airport", 8.2),
        std::make_shared<PremiumRide>("PR002", "Downtown Hotel", "Tech Park", 12.5),
        std::make_shared<StandardRide>("SR003", "Museum District", "River Walk", 5.4)
    };

    for (const RidePtr& ride : rides) {
        rider.requestRide(ride);
        driver.addRide(ride);
    }

    std::cout << "Ride Sharing System Demo" << "\n";
    std::cout << "========================" << "\n\n";

    std::cout << "Polymorphic Fare Calculation" << "\n";
    for (const RidePtr& ride : rides) {
        std::cout << ride->rideDetails() << '\n';
    }

    std::cout << "\n" << rider.viewRides();
    std::cout << driver.getDriverInfo() << '\n';

    return 0;
}
