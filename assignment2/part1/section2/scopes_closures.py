def make_counter(start):
    count = start

    def increment(step):
        nonlocal count
        count += step
        return count

    return increment


counter = make_counter(10)
print(counter(1))
print(counter(5))

items = [1, 2, 3]
closures = []
for item in items:
    closures.append(lambda: item)

print([fn() for fn in closures])
