#include <stdio.h>
#include <stdlib.h>

#define DATASET_SIZE 10

static int compare_ints(const void *a, const void *b) {
    const int left = *(const int *)a;
    const int right = *(const int *)b;
    return (left > right) - (left < right);
}

double calculate_mean(const int numbers[], int size) {
    int sum = 0;
    for (int i = 0; i < size; i++) {
        sum += numbers[i];
    }
    return (double)sum / size;
}

double calculate_median(const int numbers[], int size) {
    int *sorted = malloc((size_t)size * sizeof(int));
    if (sorted == NULL) {
        fprintf(stderr, "Memory allocation failed while calculating median.\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < size; i++) {
        sorted[i] = numbers[i];
    }

    qsort(sorted, (size_t)size, sizeof(int), compare_ints);

    double median;
    if (size % 2 == 0) {
        median = (sorted[size / 2 - 1] + sorted[size / 2]) / 2.0;
    } else {
        median = sorted[size / 2];
    }

    free(sorted);
    return median;
}

void calculate_mode(const int numbers[], int size) {
    int *sorted = malloc((size_t)size * sizeof(int));
    if (sorted == NULL) {
        fprintf(stderr, "Memory allocation failed while calculating mode.\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < size; i++) {
        sorted[i] = numbers[i];
    }

    qsort(sorted, (size_t)size, sizeof(int), compare_ints);

    int highest_count = 0;
    int current_count = 1;

    for (int i = 1; i <= size; i++) {
        if (i < size && sorted[i] == sorted[i - 1]) {
            current_count++;
        } else {
            if (current_count > highest_count) {
                highest_count = current_count;
            }
            current_count = 1;
        }
    }

    printf("Mode: ");
    current_count = 1;
    int first_mode = 1;

    for (int i = 1; i <= size; i++) {
        if (i < size && sorted[i] == sorted[i - 1]) {
            current_count++;
        } else {
            if (current_count == highest_count) {
                if (!first_mode) {
                    printf(", ");
                }
                printf("%d", sorted[i - 1]);
                first_mode = 0;
            }
            current_count = 1;
        }
    }

    printf(" (appeared %d times)\n", highest_count);
    free(sorted);
}

int main(void) {
    const int numbers[DATASET_SIZE] = {4, 7, 2, 7, 9, 2, 5, 7, 4, 2};

    printf("Dataset: ");
    for (int i = 0; i < DATASET_SIZE; i++) {
        printf("%d", numbers[i]);
        if (i < DATASET_SIZE - 1) {
            printf(", ");
        }
    }
    printf("\n");

    printf("Mean: %.2f\n", calculate_mean(numbers, DATASET_SIZE));
    printf("Median: %.2f\n", calculate_median(numbers, DATASET_SIZE));
    calculate_mode(numbers, DATASET_SIZE);

    return 0;
}
