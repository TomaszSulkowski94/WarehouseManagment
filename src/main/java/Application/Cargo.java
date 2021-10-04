package Application;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cargo {

    private Category category;
    private String description;
    private double massOfSinglePackage;
    private int numberOfPackages;
    private Warehouse assignedWarehouse;
    private LocalDate arrivalDate;


    public enum Category {
        Merchendise, Industrial, Other, Raw_Material;
    }

    public Cargo(Category category, String description, double massOfSinglePackage, int numberOfPackages, Warehouse assignedWarehouse, LocalDate arrivalDate) {
        this.category = category;
        this.description = description;
        this.massOfSinglePackage = massOfSinglePackage;
        this.numberOfPackages = numberOfPackages;
        this.assignedWarehouse = assignedWarehouse;
        this.arrivalDate = arrivalDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMassOfSinglePackage() {
        return massOfSinglePackage;
    }

    public void setMassOfSinglePackage(double massOfSinglePackage) {
        this.massOfSinglePackage = massOfSinglePackage;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public Warehouse getAssignedWarehouse() {
        return assignedWarehouse;
    }

    public void setAssignedWarehouse(Warehouse assignedWarehouse) {
        this.assignedWarehouse = assignedWarehouse;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public static void addNewCargo(List<Warehouse> whList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Wprowadź numer kategorii: \n1.Merchendise\n2.Industrial\n3.Other\n4.Raw_Material\n");
        Category category;
        switch (sc.nextInt()) {
            case 1:
                category = Category.Merchendise;
                break;
            case 2:
                category = Category.Industrial;
                break;
            case 3:
                category = Category.Other;
                break;
            case 4:
                category = Category.Raw_Material;
                break;
            default:
                System.out.println("Podano zły numer ustawiam Other");
                category = Category.Other;
        }
        sc.nextLine(); //workaround
        System.out.println("Podaj opis przesyłki");
        String descr = sc.nextLine();
        System.out.println("Podaj wagę przesyłki");
        double massOfPackage = Double.parseDouble(sc.nextLine().replace(",", "."));
        System.out.println("Podaj liczbę przesyłek");
        int numOfPackages = sc.nextInt();
        sc.nextLine(); //workaround
        System.out.println("Podaj trzy pierwsze znaki magazynu \nLista dostępnych magazynów");
        for (Warehouse w : whList) {
            System.out.println(w.getLocation());
        }
        String scVal = sc.nextLine().toUpperCase();
        Warehouse warehouse = null;
        for (Warehouse wh : whList) {
            if (wh.getLocation().toUpperCase().contains(scVal)) {
                warehouse = wh;
            }
        }
        System.out.println("Podaj oczekiwany czas przyjazdu YYYY-MM-DD");
        LocalDate arrivalDate = LocalDate.parse(sc.nextLine());
        Cargo newCargo = new Cargo(category, descr, massOfPackage, numOfPackages, warehouse, arrivalDate);
        warehouse.getCargoList().add(newCargo);
        warehouse.calculateFiledCapacity();
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

    public static void displayCargoFromWarehouseByCategory(List<Cargo> cargos) {
        List<Cargo> cl = cargos.stream().sorted(Comparator.comparing(Cargo::getCategory))
                .collect(Collectors.toList());
        displayCargoList(cl);
    }


    public static void displayCargoFromWarehouseSortedByArrivalDate(List<Cargo> cargos) {
        List<Cargo> cl = cargos.stream().sorted(Comparator.comparing(Cargo::getArrivalDate).reversed())
                .collect(Collectors.toList());
        displayCargoList(cl);
    }

    public void displayCargoDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kategoria produktu: ").append(category).append(", Opis produktu: ").append(description)
                .append(", Masa pojedynczej paczki: ").append(massOfSinglePackage).append(", Liczba paczek: ").append(numberOfPackages)
                .append(", Przypisany magazyn: ").append(assignedWarehouse.getLocation()).append(", Planowana data przywiezienia: ").append(arrivalDate);
        System.out.println(sb);
    }

    public static void removeCargo(List<Cargo> cargoList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj numer przesyłki do skasowania");
        for (int i = 0; i < cargoList.size(); i++) {
            System.out.println(i + "." + cargoList.get(i).getCategory() + " " + cargoList.get(i).getDescription() + " " + cargoList.get(i).getArrivalDate());
        }
        int val = sc.nextInt();
        Cargo cargo = cargoList.get(val);
        cargo.getAssignedWarehouse().getCargoList().remove(cargo);
        cargoList.remove(val);
    }

    @Override
    public String toString() {
        return
                "Kategoria produktu: " + category +
                        ", Opis produktu: " + description +
                        ", Masa pojedynczej paczki: " + massOfSinglePackage +
                        ", Liczba paczek: " + numberOfPackages +
                        ", Przypisany magazyn: " + assignedWarehouse.getLocation() +
                        ", Planowana data przywiezienia: " + arrivalDate;
    }
}
