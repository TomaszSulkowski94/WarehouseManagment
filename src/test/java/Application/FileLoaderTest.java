package Application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


public class FileLoaderTest {

    @Test
    public void readWarehouseList() {
        //given
        //when
        List<Warehouse> actual = FileLoader.readWarehouseList();
        //then
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void readCargoFromFile() {
        //given
        List<Warehouse> wl = FileLoader.readWarehouseList();
        //when
        List<Cargo> actual = FileLoader.readCargoFromFile(wl);
        //then
        Assert.assertFalse(actual.isEmpty());
    }
}