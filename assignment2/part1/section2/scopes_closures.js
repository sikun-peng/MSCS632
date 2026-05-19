function makeCounter(start) {
    let count = start;

    return function increment(step) {
        count += step;
        return count;
    };
}

const counter = makeCounter(10);
console.log(counter(1));
console.log(counter(5));

const closures = [];
for (let item of [1, 2, 3]) {
    closures.push(() => item);
}

console.log(closures.map((fn) => fn()));
