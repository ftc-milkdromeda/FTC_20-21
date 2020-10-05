package RobotFunctions.MecanumWheels;

import RobotFunctions.Odometry.Odometry;

/**
 * @brief Class that runs operations; class operations run on separate thread.
 */
public abstract class Drive implements Runnable{
    Drive() { this.operation = null; this.o = new Odometry(); }

    /**
     * @brief abstract method to be overided for specific implementation.
     */
    protected abstract void drive();

    /**
     * @brief Sets the operation in which the class will run as.
     * @param operation an DriveOperation to be executed.
     * @return -1 if class is already running. Operation will not be modified.
     */
    int setOperation(DriveOperation[] operation) {
        if(t.isAlive())
            return -1;
        this.operation = operation;
        return 0;

    }

    /**
     * @brief runs the thread.
     */
    public void run() {
        if(operation == null)
            return;

        o.resetOdometry();
        o.startOdometry();
         drive();
         operation = null;
         o.stopOdometry();
    }

    /**
     * @brief Starts the thread.
     */
    public void start() {
        if(running)
            return;
        t = new Thread(this, "drive");
        t.start();
    }

    public void stop() {
        t.interrupt();
        operation = null;
    }

    private boolean running = false;
    private Thread t;
    protected DriveOperation[] operation;
    protected Odometry o;
}