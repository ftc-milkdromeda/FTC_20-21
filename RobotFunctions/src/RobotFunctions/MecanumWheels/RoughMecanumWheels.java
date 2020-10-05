package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

public class RoughMecanumWheels extends MecanumWheels{
    public static RoughMecanumWheels instance(double wheelConfDiagnal, Units units, Drive drive) {
        if(MecanumWheels.isInstance)
            return null;
        return new RoughMecanumWheels(drive);
    }
    private RoughMecanumWheels(Drive drive) {
        super(drive);
    }


}
