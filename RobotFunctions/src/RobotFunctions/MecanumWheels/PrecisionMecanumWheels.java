package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

public class PrecisionMecanumWheels extends MecanumWheels{

    public static PrecisionMecanumWheels instance(Drive drive, double width, double height, Units units) {
        if(MecanumWheels.isInstance)
            return null;
        return new PrecisionMecanumWheels(drive, width, height, units);
    }

    private PrecisionMecanumWheels(Drive drive, double width, double height, Units units) {
        super(drive, width, height, units);
        this.wheelConfDiagnal = wheelConfDiagnal * units.getValue();
    }

    private double wheelConfDiagnal;
}
