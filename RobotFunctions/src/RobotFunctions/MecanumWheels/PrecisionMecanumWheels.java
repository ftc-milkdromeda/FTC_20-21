package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;
import java.util.ArrayList;

public class PrecisionMecanumWheels extends MecanumWheels
{
    public static PrecisionMecanumWheels instance(Drive drive, double width, double height, Units units) {
        if(MecanumWheels.isInstance)
            return null;
        return new PrecisionMecanumWheels(drive, width, height, units);
    }

    private PrecisionMecanumWheels(Drive drive, double width, double height, Units units)  {
        super(drive, width, height, units);
        states = new ArrayList<FinalState>();
    }

    @Override
    public int drive() {

        return 0;
    }

    @Override
    public int addTrojectory(Procedure operation) {
        if(!super.active)
            return -1;
        if(!operation.isPointMode())
            return 1;

        Procedure procedure1 = new Procedure(Math.atan(operation.getX() / operation.getY()), operation.getMagnitude(), 0);
        Procedure procedure2 = new Procedure(0, operation.getMagnitude(), 1.0);

        super.operation.add(super.calculatePath(procedure1));
        super.operation.add(super.calculatePath(procedure2));

        this.states.add(new FinalState(operation.getX(), operation.getY(), 0));
        this.states.add(new FinalState(0, 0, operation.getW()));

        super.operation.add(super.calculatePath(operation));

        return 0;
    }

    public int addTrojectory(Procedure operation[]) {
        if(!super.active)
            return -1;
        for(Procedure i : operation) {
            int returnValue = addTrojectory(i);
            if (returnValue == -1)
                return -1;
            if(returnValue == 1)
                return 1;

        }
        return 0;
    }

    @Override
    public int resetOperation() {
        if(super.resetOperation() == -1)
            return -1;

        this.states = new ArrayList<FinalState>();

        return 0;
    }

    private class FinalState {
        private FinalState(double x, double y, double w) {
            this.x = x;
            this.y = y;
            this.w = w;
        }

        private double x;
        private double y;
        private double w;
    }

    ArrayList<FinalState> states;
}
