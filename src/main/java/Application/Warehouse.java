package Application;

import java.util.*;


public class Warehouse {

    private String location;
    private double capacity;
    private List<Cargo> cargoList;
    private double filledCapacity;

    public Warehouse(String location, double capacity) {
        this.location = location;
        this.capacity = capacity;
        this.cargoList = new ArrayList<>();
        this.filledCapacity = 0;
    }

    public void calculateFiledCapacity() {
        filledCapacity = 0;
        for (Cargo cargo : cargoList) {
            filledCapacity += cargo.getMassOfSinglePackage() * cargo.getNumberOfPackages();
        }
        setFilledCapacity(filledCapacity);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    public double getFilledCapacity() {
        return filledCapacity;
    }

    public void setFilledCapacity(double filledCapacity) {
        this.filledCapacity = filledCapacity;
    }

    private static void displayCargoList(List<Cargo> cl) {
        for (int i = 0; i < cl.size(); i++) {
            System.out.println(i + ".Kategoria produktu: " + cl.get(i).getCategory() +
                    ", Opis produktu: " + cl.get(i).getDescription() +
                    ", Masa pojedynczej paczki: " + cl.get(i).getMassOfSinglePackage() +
                    ", Liczba paczek: " + cl.get(i).getNumberOfPackages() +
                    ", Przypisany magazyn: " + cl.get(i).getAssignedWarehouse().getLocation() +
                    ", Planowana data przywiezienia: " + cl.get(i).getArrivalDate().toString()
            );
        }
    }

    private static void displayWarehouseList(List<Warehouse> wl) {
        for (int i = 0; i < wl.size(); i++) {
            System.out.println(i + "." + wl.get(i).getLocation());
        }
    }

    public static void moveCargo(List<Warehouse> wl) {
        System.out.println("Z którego magazynu chciałbyś przenieść przesyłkę?");
        displayWarehouseList(wl);
        Scanner scanner = new Scanner(System.in);
        try {
            Warehouse oldWarehouse = wl.get(scanner.nextInt());
            List<Cargo> cargos = oldWarehouse.getCargoList();
            displayCargoList(cargos);
            System.out.println("Wybierz paczkę, którą chcesz przenieść?");
            int val = scanner.nextInt();
            Cargo cargo = cargos.get(val);
            System.out.println("Do którego magazynu chcesz dodać paczkę?");
            displayWarehouseList(wl);
            Warehouse newWarehouse = wl.get(scanner.nextInt());
            cargo.setAssignedWarehouse(newWarehouse);
            newWarehouse.getCargoList().add(cargo);
            oldWarehouse.getCargoList().remove(val);
            oldWarehouse.calculateFiledCapacity();
            newWarehouse.calculateFiledCapacity();
        } catch (Exception e) {
            System.out.println("Coś poszło nie tak");
            System.exit(1);
        }
    }

    public static void displayNearlyFullWarehouses(List<Warehouse> wl) {
        for (Warehouse wh : wl) {
            wh.calculateFiledCapacity();
            if ((wh.filledCapacity / wh.capacity) * 100 > 80.0) {
                System.out.println(wh.location);
            }
        }
    }

    public static void displayList(List<Warehouse> w) {
        for (Warehouse warehouse : w) {
            System.out.println(warehouse.toString());
        }
    }

    public static void displayEmptyWarehouses(List<Warehouse> wl) {
        for (Warehouse wh : wl) {
            wh.calculateFiledCapacity();
            if ((wh.filledCapacity / wh.capacity) * 100 < 20.0) {
                System.out.println(wh.location);
            }
        }
    }

    public static void displayFilledCapacityOfWarehouse(List<Warehouse> wl) {
        for (Warehouse w : wl) {
            w.calculateFiledCapacity();
            double filledCapacity = (w.filledCapacity / w.capacity) * 100;
            filledCapacity = Math.round(filledCapacity * 100);
            filledCapacity = filledCapacity / 100;
            System.out.println((w.location + " " + filledCapacity + "%"));
        }
    }

    @Override
    public String toString() {
        return "Lokacjia:" +
                location +
                " Pojemność:" + capacity
                ;
    }
}
