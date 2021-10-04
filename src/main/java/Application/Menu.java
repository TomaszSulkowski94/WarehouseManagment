package Application;

import java.util.*;

import static Application.Cargo.*;
import static Application.FileLoader.readCargoFromFile;
import static Application.FileLoader.readWarehouseList;
import static Application.FileSave.saveCargoFile;
import static Application.FileSave.saveWarehouseFile;
import static Application.Warehouse.*;

public class Menu {

    public static int getnum() {
        Scanner scan = new Scanner(System.in);
        int testint;
        try {
            testint = scan.nextInt();
        } catch (InputMismatchException e) {
            scan.close();
            return 99;
        }
        return testint;
    }


    public static void run() {
        List<Warehouse> warehouses = readWarehouseList();
        List<Cargo> cargos = readCargoFromFile(warehouses);
        mainMenu(warehouses, cargos);
    }

    public static void mainMenu(List<Warehouse> warehouses, List<Cargo> cargos) {
        System.out.println("\nJaką operację chcesz wykonać? \n1.Wyświetl prawie pełne magazyny " +
                "\n2.Wyświetl prawie puste magazyny \n3.Wyświetl przesyłki dla magazynu na podstawie kategorii " +
                "\n4.Wyświetl przesyłki wg. daty przyjazdu \n5.Przenieś przesyłkę \n6.Dodaj nową przesyłkę " +
                "\n7.Usuń ładunek \n8.Wyświetl zapełnienie magazynów w % \n9.Opuść program");
        switch (getnum()) {
            case 1:
                System.out.println("Lista magazynów, których zajęta pojemność jest większa niż 80%");
                displayNearlyFullWarehouses(warehouses);
                mainMenu(warehouses, cargos);
                break;
            case 2:
                System.out.println("Lista magazynów, których zajęta pojemność jest mniejsza niż 20%");
                displayEmptyWarehouses(warehouses);
                mainMenu(warehouses, cargos);
                break;
            case 3:
                displayCargoFromWarehouseByCategory(cargos);
                mainMenu(warehouses, cargos);
                break;
            case 4:
                displayCargoFromWarehouseSortedByArrivalDate(cargos);
                mainMenu(warehouses, cargos);
                break;
            case 5:
                moveCargo(warehouses);
                mainMenu(warehouses, cargos);
                break;
            case 6:
                addNewCargo(warehouses);
                mainMenu(warehouses, cargos);
                break;
            case 7:
                removeCargo(cargos);
                mainMenu(warehouses, cargos);
                break;
            case 8:
                displayFilledCapacityOfWarehouse(warehouses);
                mainMenu(warehouses,cargos);
                break;
            case 9:
                saveCargoFile(cargos);
                saveWarehouseFile(warehouses);
                System.exit(0);
                break;
            case 99:
                System.out.println("Użytkownik podał zły numer operacji. Spróbuj ponownie");
                mainMenu(warehouses, cargos);
                break;
            default:
                System.out.println("Użytkownik podał zły numer operacji. Spróbuj ponownie");
                mainMenu(warehouses, cargos);
        }
    }
}
