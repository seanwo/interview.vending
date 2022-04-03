import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineImplTest {

    VendingMachine vm = null;

    @BeforeEach
    public void setup() {
        vm = VendingMachineFactory.getVendingMachine();
    }

    @AfterEach
    public void tearDown() {
        vm = null;
    }

    @Test
    void insertCoinQuarter() {
        vm.insertCoin(Coin.QUARTER);
        VendingMachineImpl vmi = (VendingMachineImpl) vm;
        assertEquals(25, vmi.getCurrentBalance());
    }

    @Test
    void insertCoinOneOfEach() {
        for (Coin c : Coin.values()) {
            vm.insertCoin(c);
        }
        VendingMachineImpl vmi = (VendingMachineImpl) vm;
        assertEquals(41, vmi.getCurrentBalance());
    }

    @Test
    void refund() {
        for (Coin c : Coin.values()) {
            vm.insertCoin(c);
        }
        VendingMachineImpl vmi = (VendingMachineImpl) vm;
        assertEquals(41, vmi.getCurrentBalance());
        List<Coin> refund = vm.refund();
        assertEquals(0, vmi.getCurrentBalance());
        assertEquals(Coin.values().length, refund.size());
        for (Coin c : Coin.values()) {
            assertTrue(refund.contains(c));
        }
    }

    @Test
    void selectItemGetPrice() {
        for (Item item : Item.values()) {
            long price = vm.selectItemGetPrice(item);
            assertEquals(item.getPrice(), price);
        }
    }

    @Test
    void vendItem() {
        vm.insertCoin(Coin.QUARTER);
        vm.selectItemGetPrice(Item.COKE);
        Bundle bundle = vm.vend();
        assertEquals(Item.COKE, bundle.getItem());
        int change = 0;
        for (Coin c : bundle.getChange()) {
            change += c.getValue();
        }
        assertEquals(Coin.QUARTER.getValue() - Item.COKE.getPrice(), change);
    }

    @Test
    void vendItemSoldOut() {
        for (int i = 0; i < 5; i++) {
            vm.insertCoin(Coin.DIME);
            vm.selectItemGetPrice(Item.COKE);
            vm.vend();
        }
        vm.insertCoin(Coin.DIME);
        assertThrows(SoldOutException.class, () -> vm.selectItemGetPrice(Item.COKE));
    }

    @Test
    void vendItemInsufficientFunds() {
        vm.selectItemGetPrice(Item.COKE);
        assertThrows(InsufficientFundsException.class, () -> vm.vend());
    }

    @Test
    void vendItemInvalidVend() {
        assertThrows(InvalidVendException.class, () -> vm.vend());
    }

    @Test
    void vendItemInsufficientChange() {
        vm.insertCoin(Coin.DIME);
        vm.selectItemGetPrice(Item.DRPEPPER);
        vm.vend();
        vm.insertCoin(Coin.DIME);
        vm.selectItemGetPrice(Item.DRPEPPER);
        assertThrows(InsufficientChangeException.class, () -> vm.vend());
    }
}