package Application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static List<Warehouse> readWarehouseList() {
        List<Warehouse> csv = new ArrayList<>();
        try {
            Path path = Paths.get("Warehouses.csv");
            BufferedReader bufferedReade = Files.newBufferedReader(path);
            bufferedReade.readLine(); //odpowiada za pominięcie nagłówka z kolumnami
            String line = bufferedReade.readLine();
            while (line != null) {
                String[] att = line.split(";");
                Warehouse warehouse = createWarehouse(att);
                csv.add(warehouse);
                line = bufferedReade.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csv;
    }

    private static Warehouse createWarehouse(String[] attributes) {
        return new Warehouse(attributes[0], Double.parseDouble(attributes[1]));
    }

    private static Cargo createCargo(String[] attributes, List<Warehouse> list) {
        Cargo.Category category;
        try {
            category = Cargo.Category.valueOf(attributes[0]);
        } catch (Exception e) {
            category = Cargo.Category.Other;
        }
        String description = attributes[1];
        double massOfSinglePackage = Double.parseDouble(attributes[2].replace(",", "."));
        int numberOfPackages = Integer.parseInt(attributes[3]);
        Warehouse assignedWarehouse = null;
        for (Warehouse w : list) {
            if (w.getLocation().equalsIgnoreCase(attributes[4])) {
                assignedWarehouse = w;
            }
        }
        LocalDate arrivalDate = LocalDate.parse(attributes[5]);
        return new Cargo(category, description, massOfSinglePackage, numberOfPackages, assignedWarehouse, arrivalDate);
    }

    public static List<Cargo> readCargoFromFile(List<Warehouse> wl) {
        List<Cargo> csvCargo = new ArrayList<>();
        try {
            Path path = Paths.get("Cargo.csv");
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] att = line.split(";");
                Cargo cargo = createCargo(att, wl);
                csvCargo.add(cargo);
                line = bufferedReader.readLine();
                cargo.getAssignedWarehouse().getCargoList().add(cargo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvCargo;
    }
}
