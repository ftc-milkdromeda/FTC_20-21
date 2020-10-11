package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

public class RoughMecanumWheels extends MecanumWheels{
    public static RoughMecanumWheels instance(Drive drive, double width, double height, Units units) {
        if(MecanumWheels.isInstance)
            return null;
        return new RoughMecanumWheels(drive, width, height, units);
    }
    protected RoughMecanumWheels(Drive drive, double width, double height, Units units) {
        super(drive, width, height, units);
        super.operation.add(null);
    }

    @Override
    public int drive() {
        if(!super.active)
            return -1;
        if(operation.size() == 0)
            return 1;
        super.drive.setMoters(super.operation.get(0).getMoters());
        return 0;
    }

    @Override
    public int addTrojectory(Procedure operation) {
        if(!super.active)
            return -1;
        if(!operation.isVectorMode())
            return 1;

        super.operation.set(0, super.calculatePath(operation));

        return 0;
    }
}
