package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;
import java.util.ArrayList;

/**
 * @brief Controls mecanum wheels operations. Class is a state machine.
 * @author Tyler Wang
 */
public abstract class MecanumWheels {
    protected MecanumWheels(Drive drive) {
        this.drive = drive;
    }

    void deleteObject() {
        active = false;
        MecanumWheels.isInstance = false;
    }
    protected boolean checkActivity() { return this.active; }

    protected  boolean active = true;
    protected static boolean isInstance = false;
    protected Drive drive;
}
