package Milkdromda.Drivers;

import Milkdromda.Drivers.Template.Driver;

import java.util.ArrayList;

public class DriverManager {

    private DriverManager() {}

    public static void stopProcess(int index) {
        DriverManager.drivers.get(index).terminate();
        DriverManager.drivers.set(index, null);

        DriverManager.numOfDrivers--;
    }
    public static int attachProcess(Driver driver) {
        DriverManager.drivers.add(driver);

        DriverManager.numOfDrivers++;

        return DriverManager.drivers.size() - 1;
    }
    public static void stopAllProcess() {
        for(Driver driver : DriverManager.drivers)
            if(driver != null)
                driver.terminate();

        DriverManager.drivers = new ArrayList<Driver>();
        DriverManager.numOfDrivers = 0;
    }

    public static int numOfProcess() {
        return DriverManager.numOfDrivers;
    }

    private static ArrayList<Driver> drivers = new ArrayList<Driver>();
    private static int numOfDrivers = 0;
}
