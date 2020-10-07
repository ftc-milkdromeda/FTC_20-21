package RobotFunctions.MecanumWheels;

import RobotFunctions.Odometry.Odometry;
import RobotFunctions.Odometry.RobotState;
import RobotFunctions.Units;

/**
 * @brief Class that runs operations; class operations run on separate thread.
 */
public abstract class Drive{
    public Drive() {
        this.odometry = new Odometry();
        this.power = new double[4];
    }

    public void stop() {
        this.setMoter(Moter.UPPER_RIGHT, 0);
        this.setMoter(Moter.LOWER_RIGHT, 0);
        this.setMoter(Moter.LOWER_LEFT, 0);
        this.setMoter(Moter.UPPER_LEFT, 0);
    }

    public abstract void setMoter(Moter index, double power);
    public abstract void setMoters(double[] power);

    protected Odometry odometry;
    protected double[] power;
}