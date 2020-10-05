package RobotFunctions.MecanumWheels;

import RobotFunctions.Odometry.Odometry;
import RobotFunctions.Odometry.RobotState;
import RobotFunctions.Units;

/**
 * @brief Class that runs operations; class operations run on separate thread.
 */
public abstract class Drive implements Runnable{
    public Drive() {
        this.odometry = new Odometry();
    }

    public void stop() {
        for(int a = 0; a < 4; a++)
            this.setMoter(a, 0);
    }

    public void stop(double distance, Units units) {
        RobotState state = odometry.getVelocity();
        double[] velocity = state.getPolar();

        //TODO add gradient stop
    }

    public abstract void setMoter(Moter index, double power);
    public abstract void setMoter(int index, double power);

    protected Odometry odometry;
    protected double[] power;
}