package Milkdromda.RobotFunctions.MecanumWheels;

import Milkdromda.Drivers.DriveTrain;
import Milkdromda.RobotFunctions.Units_length;

public class RoughMecanumWheels extends MecanumWheels{
    public static RoughMecanumWheels instance(DriveTrain driveTrain, double width, double height, Units_length units) {
        if(MecanumWheels.isInstance)
            return null;
        return new RoughMecanumWheels(driveTrain, width, height, units);
    }
    protected RoughMecanumWheels(DriveTrain driveTrain, double width, double height, Units_length units) {
        super(driveTrain, width, height, units);
        super.operation.add(null);
    }

    @Override
    public int drive() {
        if(!super.active)
            return -1;
        if(operation.size() == 0)
            return 1;
        super.driveTrain.setMotors(super.operation.get(0).getMotors());
        return 0;
    }

    @Override
    public int addTrajectory(Procedure operation) {
        if(!super.active)
            return -1;
        if(!operation.isVectorMode())
            return 1;

        super.operation.set(0, super.calculatePath(operation));

        return 0;
    }
}
