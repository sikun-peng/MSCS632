class StatisticsCalculator:
    def __init__(self, numbers):
        self.numbers = numbers

    def mean(self):
        return sum(self.numbers) / len(self.numbers)

    def median(self):
        sorted_numbers = sorted(self.numbers)
        size = len(sorted_numbers)
        middle = size // 2

        if size % 2 == 0:
            return (sorted_numbers[middle - 1] + sorted_numbers[middle]) / 2
        return sorted_numbers[middle]

    def mode(self):
        frequencies = {}
        for number in self.numbers:
            frequencies[number] = frequencies.get(number, 0) + 1

        highest_count = max(frequencies.values())
        modes = [number for number, count in frequencies.items() if count == highest_count]
        return sorted(modes), highest_count

    def display_statistics(self):
        modes, count = self.mode()
        print("Dataset:", ", ".join(str(number) for number in self.numbers))
        print(f"Mean: {self.mean():.2f}")
        print(f"Median: {self.median():.2f}")
        print(f"Mode: {', '.join(str(mode) for mode in modes)} (appeared {count} times)")


def main():
    numbers = [4, 7, 2, 7, 9, 2, 5, 7, 4, 2]
    calculator = StatisticsCalculator(numbers)
    calculator.display_statistics()


if __name__ == "__main__":
    main()
