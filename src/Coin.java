public enum Coin {
    PENNY(1),
    NICKLE(5),
    DIME(10),
    QUARTER(25);

    private long value = 0;

    Coin(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }
}
