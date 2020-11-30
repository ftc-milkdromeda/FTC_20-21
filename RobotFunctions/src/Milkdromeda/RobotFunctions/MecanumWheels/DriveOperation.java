package Milkdromeda.RobotFunctions.MecanumWheels;

import java.util.ArrayList;

import RobotFunctions.Error;

public class DriveOperation extends Thread{
    public DriveOperation() {
        this.mecanumWheelsOperations = new ArrayList<MecanumWheels>();
        this.template = null;
        this.object = null;
        this.cursor = -1;
    }
    public DriveOperation(MecanumWheels template) {
        this();
        this.template = template;
    }

    public void setCursor(int index) {
        if (index < -1 || index >= this.mecanumWheelsOperations.size()) {
            this.setError(Error.ARGUMENTS_OUT_OF_BOUND);
            return;
        }
        if (index == -1) {
            this.cursor = -1;
        }
        else
            this.cursor = index;

        this.setError(Error.NO_ERROR);
    }
    public void remove() {
        if(this.cursor == -1)
            this.delete();
        else
            this.delete(this.cursor);
    }
    public void add(MecanumWheels object) {
        if(this.cursor == -1)
            this.set(object);
        else
            this.set(this.cursor, object);
    }

    public MecanumWheels get(int index) {
        if(index >= this.mecanumWheelsOperations.size()) {
            this.setError(Error.ARGUMENTS_OUT_OF_BOUND);
            return null;
        }

        this.setError(Error.NO_ERROR);
        return this.mecanumWheelsOperations.get(index);
    }
    public void set(int index, MecanumWheels operation) {
        this.mecanumWheelsOperations.set(index, operation);

        this.setError(Error.NO_ERROR);
    }
    public void set(MecanumWheels operation) {
        this.mecanumWheelsOperations.add(operation);

        this.setError(Error.NO_ERROR);
    }
    public void delete() {
        this.delete(this.mecanumWheelsOperations.size() - 1);
    }
    public void delete(int index) {
        if(index >= this.mecanumWheelsOperations.size()) {
            this.setError(Error.ARGUMENTS_OUT_OF_BOUND);
            return;
        }

        this.mecanumWheelsOperations.remove(index);
    }

    @Override
    public void run() {
        if(DriveOperation.isRunning) {
            this.setError(Error.PROCESS_ALREADY_RUNNING);
            return;
        }

        this.progress = 0;
        this.isRunning = true;

        while(this.progress < this.mecanumWheelsOperations.size()) {
            
        }

        this.isRunning = false;
    }
    public int next() {
    }


    public int getProgress() {}
    public int getNumOfOperations() {}

    private void setError(Error error) {
        this.error = error;
    }
    public Error getError() {
        return this.error;
    }
    private Error error;

    private ArrayList<MecanumWheels> mecanumWheelsOperations;
    private MecanumWheels template;
    private int cursor;
    private int progress;
    private Drive driveConfig;

    private static boolean isRunning = false;

    public MecanumWheels object;
}
