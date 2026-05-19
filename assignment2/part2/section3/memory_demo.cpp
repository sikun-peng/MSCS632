#include <iostream>

using namespace std;

int main() {
    int* numbers = new int[1000000];
    cout << "Allocated 1000000 integers" << endl;

    numbers[0] = 42;
    cout << "First value: " << numbers[0] << endl;

    delete[] numbers;
    numbers = nullptr;

    cout << "Memory released manually with delete[]" << endl;
    return 0;
}
