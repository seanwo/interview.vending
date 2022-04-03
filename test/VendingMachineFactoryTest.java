import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VendingMachineFactoryTest {
    @Test
    void getVendingMachine() {
        VendingMachine vm = null;
        vm = VendingMachineFactory.getVendingMachine();
        assertNotEquals(null, vm);
    }

    @Test
    void getDifferentVendingMachines() {
        VendingMachine vm1 = VendingMachineFactory.getVendingMachine();
        VendingMachine vm2 = VendingMachineFactory.getVendingMachine();
        assertNotEquals(vm1, vm2);
    }

}