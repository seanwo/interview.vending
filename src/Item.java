public enum Item {
    PEPSI(11),
    COKE(10),
    DRPEPPER(6),
    SPRINT(5);

    private long price = 0;

    Item(long price) {
        this.price = price;
    }

    public long getPrice() {
        return this.price;
    }

}
