import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {
    private HashMap<Coin, Integer> coinInventory = new HashMap<>();
    private HashMap<Item, Integer> itemInventory = new HashMap<>();
    private long currentBalance = 0;
    private Item currentItem = null;

    public long getCurrentBalance() {
        return currentBalance;
    }

    public VendingMachineImpl() {
        for (Coin c : Coin.values()) {
            coinInventory.put(c, 5);
        }
        for (Item i : Item.values()) {
            itemInventory.put(i, 5);
        }
    }

    public void insertCoin(Coin c) {
        int count = 1;
        if (coinInventory.containsKey(c)) {
            count += coinInventory.get(c);
        }
        coinInventory.put(c, count);
        currentBalance += c.getValue();
    }

    public List<Coin> refund() {
        return returnBalance();
    }

    private Boolean retrieveCoin(Coin c) {
        if (coinInventory.containsKey(c)) {
            int count = coinInventory.get(c);
            count--;
            if (count <= 0) {
                coinInventory.remove(c);
            } else {
                coinInventory.put(c, count);
            }
        } else {
            return false;
        }
        return true;
    }

    private Boolean reduceItemInventory(Item item) {
        if (itemInventory.containsKey(item)) {
            int count = itemInventory.get(item);
            count--;
            if (count <= 0) {
                itemInventory.remove(item);
            } else {
                itemInventory.put(item, count);
            }
        } else {
            return false;
        }
        return true;
    }

    private List<Coin> returnBalance() {
        ArrayList<Coin> balance = new ArrayList<>();
        while (currentBalance > 0) {
            if ((currentBalance >= Coin.QUARTER.getValue()) && coinInventory.containsKey(Coin.QUARTER)) {
                retrieveCoin(Coin.QUARTER);
                currentBalance -= Coin.QUARTER.getValue();
                balance.add(Coin.QUARTER);
                continue;
            }
            if ((currentBalance >= Coin.DIME.getValue()) && coinInventory.containsKey(Coin.DIME)) {
                retrieveCoin(Coin.DIME);
                currentBalance -= Coin.DIME.getValue();
                balance.add(Coin.DIME);
                continue;
            }
            if ((currentBalance >= Coin.NICKLE.getValue()) && coinInventory.containsKey(Coin.NICKLE)) {
                retrieveCoin(Coin.NICKLE);
                currentBalance -= Coin.NICKLE.getValue();
                balance.add(Coin.NICKLE);
                continue;
            }
            if ((currentBalance >= Coin.PENNY.getValue()) && coinInventory.containsKey(Coin.PENNY)) {
                retrieveCoin(Coin.PENNY);
                currentBalance -= Coin.PENNY.getValue();
                balance.add(Coin.PENNY);
                continue;
            }
            throw new InsufficientChangeException("Coin inventory inadequate to provide change");
        }
        return balance;
    }

    public long selectItemGetPrice(Item item) {
        if (!itemInventory.containsKey(item)) {
            throw new SoldOutException("Out of selected item");
        }
        currentItem = item;
        return item.getPrice();
    }

    public Bundle vend() {
        if (null == currentItem) throw new InvalidVendException("No item selected");
        if (currentItem.getPrice() > currentBalance) {
            throw new InsufficientFundsException("Please insert more coins for selected item");
        }
        currentBalance -= currentItem.getPrice();
        List<Coin> change = returnBalance();
        reduceItemInventory(currentItem);
        Item item = currentItem;
        currentItem = null;
        return new Bundle(item, change);
    }
}
