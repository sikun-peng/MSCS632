fn main() {
    let numbers = vec![0_u64; 1_000_000];
    println!("Allocated {} integers", numbers.len());

    let first = &numbers[0];
    println!("Borrowed first value: {}", first);

    consume(numbers);
}

fn consume(data: Vec<u64>) {
    println!("Ownership moved into consume(): {}", data.len());
}
