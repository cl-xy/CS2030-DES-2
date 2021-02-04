package cs2030.simulator;

public class Pair<T, U> {
    private final T key;
    private final U value;

    private Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }

    public static <T, U> Pair<T, U> of(T key, U value) {
        return new Pair<T, U>(key, value);
    }

    public T first() {
        return this.key;
    }

    public U second() {
        return this.value;
    }
}
