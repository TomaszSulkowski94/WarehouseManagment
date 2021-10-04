package Application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;


public class FileSave {
    public static void saveCargoFile(List<Cargo> cargoList) {
        try {
            FileWriter fs = new FileWriter("CargoOutput.csv");
            BufferedWriter out = new BufferedWriter(fs);
            out.write("Category;Description;Mass of single package;Number of packages;Assigned warehouse;Arrival date\n");
            for (Cargo cg : cargoList) {
                String row = cg.getCategory().toString() + ";" + cg.getDescription() + ";" + cg.getMassOfSinglePackage() + ";"
                        + cg.getNumberOfPackages() + ";" + cg.getAssignedWarehouse().getLocation() + ";" + cg.getArrivalDate() + "\n";
                out.write(row);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveWarehouseFile(List<Warehouse> whList) {
        try {
            FileWriter fs = new FileWriter("WarehouseOutput.csv");
            BufferedWriter bf = new BufferedWriter(fs);
            bf.write("Location;Capacity\n");
            for (Warehouse wh : whList) {
                String row = wh.getLocation() + ";" + wh.getCapacity();
                bf.write(row);
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}