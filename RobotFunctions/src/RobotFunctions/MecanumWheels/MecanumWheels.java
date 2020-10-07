package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;
import java.util.ArrayList;

/**
 * @brief Controls mecanum wheels operations. Class is a state machine.
 * @author Tyler Wang
 */
public abstract class MecanumWheels {
    protected MecanumWheels(Drive drive, double width, double height, Units units) {
        this.drive = drive;
        this.width = width * units.getValue();
        this.height = height * units.getValue();
    }

    void deleteObject() {
        active = false;
        MecanumWheels.isInstance = false;
    }
    protected boolean checkActivity() { return this.active; }
    protected double pivotPointCalculation(double x) {
        double a = 2.5;
        //funtion: a * x^-1 - a
        if(x == 0)
            return -1;
        return a / x -a * Math.signum(x);
    }

    protected  boolean active = true;
    protected static boolean isInstance = false;
    protected Drive drive;
    protected double width;
    protected  double height;
}
