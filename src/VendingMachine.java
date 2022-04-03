import java.util.List;

public interface VendingMachine {
    public void insertCoin(Coin c);

    public List<Coin> refund();

    public long selectItemGetPrice(Item item);

    public Bundle vend();
}
