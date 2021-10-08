package Application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Application.Cargo.Category.Industrial;


public class FileSaveTest {

    @Test
    public void saveCargoFile() {
        //given
        Warehouse loc = new Warehouse("Location", 1000);
        List<Cargo> cgList = new ArrayList<>();
        Cargo cargo = new Cargo(Industrial, "Description", 100.0, 1,loc,LocalDate.parse("2021-11-09"));
        cgList.add(cargo);
        //when
        FileSave.saveCargoFile(cgList);
        File file = new File("WarehouseOutput.csv");
        //then
        Assert.assertTrue(file.length() > 0);
    }

    @Test
    public void saveWarehouseFile() {
        //given
        Warehouse warehouse = new Warehouse("LOCATION A", 1000);
        List<Warehouse> whList = Arrays.asList(warehouse);
        //when
        FileSave.saveWarehouseFile(whList);
        File file = new File("WarehouseOutput.csv");
        //then
        Assert.assertTrue(file.length() > 0);
    }
}