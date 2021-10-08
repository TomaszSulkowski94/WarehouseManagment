package Application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WarehouseTest {
    private Warehouse testWarehouse;
    private List<Cargo> cargoList;
    private Cargo cargo1;
    private Cargo cargo2;

    @Before
    public void setUp() throws Exception {
        cargoList = new ArrayList<>();
        testWarehouse = new Warehouse("Katowice", 1000);
        cargo1 = new Cargo(Cargo.Category.Other, "TEST_MOVE_DESCRIPTION", 20, 1, testWarehouse, LocalDate.parse("2021-06-16"));
        cargo2 = new Cargo(Cargo.Category.Other, "Description 2", 20, 1, testWarehouse, LocalDate.parse("2021-06-16"));
        testWarehouse.getCargoList().add(cargo1);
        testWarehouse.getCargoList().add(cargo2);
        cargoList.add(cargo1);
        cargoList.add(cargo2);
    }

    @Test
    public void calculateFiledCapacity() {
        //given
        //when
        testWarehouse.calculateFiledCapacity();
        //then
        Assert.assertTrue(testWarehouse.getFilledCapacity() == 40);
    }

    @Test
    public void moveCargo() {
        //given
        List<Warehouse> wl = new ArrayList<>();
        wl.add(testWarehouse);
        Warehouse w2 = new Warehouse("Opole", 1500);
        wl.add(w2);
        //when
        ByteArrayInputStream inputs = new ByteArrayInputStream(("0\n0\n1").getBytes(StandardCharsets.UTF_8));
        System.setIn(inputs);
        Warehouse.moveCargo(wl);
        //then
        Assert.assertTrue(w2.getCargoList().stream().filter(cargo -> cargo.getDescription().equalsIgnoreCase("TEST_MOVE_DESCRIPTION"))!=null);
    }

}