import java.util.List;

public class Bundle {
    private Item item = null;
    private List<Coin> change = null;

    public Bundle(Item item, List<Coin> change) {
        this.item = item;
        this.change = change;
    }

    public Item getItem() {
        return this.item;
    }

    public List<Coin> getChange() {
        return this.change;
    }
}
