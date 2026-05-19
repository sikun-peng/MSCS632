#include <iostream>
#include <vector>
#include <functional>

using namespace std;

function<int(int)> makeCounter(int start) {
    int count = start;
    return [count](int step) mutable {
        count += step;
        return count;
    };
}

int main() {
    auto counter = makeCounter(10);
    cout << counter(1) << endl;
    cout << counter(5) << endl;

    vector<function<int()>> closures;
    for (int item : {1, 2, 3}) {
        closures.push_back([item]() { return item; });
    }

    for (const auto& closure : closures) {
        cout << closure() << " ";
    }
    cout << endl;

    return 0;
}
