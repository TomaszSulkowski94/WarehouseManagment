package Application;

import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class CargoTest {

    private static List<Warehouse> wl;
    private static List<Cargo> cgList;
    private static Warehouse katowice;
    private static ByteArrayInputStream in;

    @BeforeClass
    public static void init() {
        wl = new ArrayList<>();
        katowice = new Warehouse("Katowice", 1000);
        wl.add(katowice);
        cgList = new ArrayList<>();
        cgList.add(new Cargo(Cargo.Category.Other, "Description 1", 20, 1, katowice, LocalDate.parse("2021-06-16")));
        cgList.add(new Cargo(Cargo.Category.Industrial, "Description 2", 20, 1, katowice, LocalDate.parse("2020-09-11")));
        cgList.add(new Cargo(Cargo.Category.Raw_Material, "Description 3", 20, 1, katowice, LocalDate.parse("2021-12-11")));
    }

    @Test
    public void addNewCargo() {
        //given
        in = new ByteArrayInputStream("1\nFOR_TESTING_PURPOSE\n20\n1\nkat\n2021-12-11".getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        Cargo.addNewCargo(wl);
        //then
        Assert.assertTrue(wl.get(0).getCargoList().stream().filter(c -> c.getDescription().equalsIgnoreCase("for_testing_purpose")) != null);
    }

    @Test
    public void addNewCargoWrongComma() {
        //given
        in = new ByteArrayInputStream("1\nWRONG_SEPARATOR\n20,1\n1\nkat\n2021-12-11".getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        //when
        Cargo.addNewCargo(wl);
        //then
        Assert.assertTrue(wl.get(0).getCargoList().stream().filter(cargo -> cargo.getDescription().equalsIgnoreCase("WRONG_SEPARATOR")) != null);
    }

    @Test(expected = DateTimeParseException.class)
    public void addNewCargoException() {
        //given
        in = new ByteArrayInputStream("1\nWRONG_DATA\n20,1\n1\nkat\n2021.12.11".getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        //when
        Cargo.addNewCargo(wl);
        //then
    }

    @Test
    public void removeCargo() {
        //given
        Cargo toBeDeleted = new Cargo(Cargo.Category.Raw_Material, "TO BE DELETED AT THIS TEST", 20, 1, katowice, LocalDate.parse("2021-12-11"));
        cgList.add(0, toBeDeleted);
        //when
        in = new ByteArrayInputStream("0".getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        Cargo.removeCargo(cgList);
        //then
        Assert.assertFalse(cgList.contains(toBeDeleted));
    }

    @AfterClass
    public static void close() throws IOException {
        in.close();
    }
}