public class MemoryDemo {
    public static void main(String[] args) {
        int[] numbers = new int[1_000_000];
        System.out.println("Allocated " + numbers.length + " integers");

        numbers = null;
        System.gc();

        System.out.println("Array reference cleared; garbage collector may reclaim memory.");
    }
}
