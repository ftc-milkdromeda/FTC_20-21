package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

public class PrecisionMecanumWheels extends MecanumWheels{
    /**
     * @brief Used to create MecanumWheels instances.
     * @param wheelConfDiagnal the length between two diagnal wheels.
     * @param units Units used in for the wheelConfDiagnal
     * @param drive an subclass of the Drive class to be used to drive the trolly
     * @return returns new instance; returns null if instance already exist.
     */
    public static PrecisionMecanumWheels instance(double wheelConfDiagnal, Units units, Drive drive) {
        if(MecanumWheels.isInstance)
            return null;
        return new PrecisionMecanumWheels(wheelConfDiagnal, units, drive);
    }

    private PrecisionMecanumWheels(double wheelConfDiagnal, Units units, Drive drive) {
        super(drive);
        this.wheelConfDiagnal = wheelConfDiagnal * units.getValue();
    }

    private double wheelConfDiagnal;
}
