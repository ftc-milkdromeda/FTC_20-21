package RobotFunctions.MecanumWheels;

import Drivers.DriveTrain;
import RobotFunctions.Units_length;

import java.util.ArrayList;

/**
 * @brief Class used for exact movement of the drive train.
 */
public class PrecisionMecanumWheels extends MecanumWheels
{
    /**
     * @brief Makes a new instance of the PrecisionMecanumWheels class.
     * @param driveTrain An instance of a subclass of Drive.
     * @param width The distance between the two front wheels on the drive train.
     * @param height The distance between the two side wheels on the drive train.
     * @param units The units used to measure the distance of the wheels.
     * @return Returns a new instance of PrecisionMecanumWheels; returns null if an error occurred.
     */
    public static PrecisionMecanumWheels instance(DriveTrain driveTrain, double width, double height, Units_length units) {
        if(MecanumWheels.isInstance)
            return null;
        return new PrecisionMecanumWheels(driveTrain, width, height, units);
    }

    /**
     * @brief Constructor for PrecisonMecanumWheels class.
     * @param driveTrain An instance of a subclass of Drive.
     * @param width The distance between the two front wheels on the drive train.
     * @param height The distance between the two side wheels on the drive train.
     * @param units The units used to measure the distance of the wheels.
     */
    private PrecisionMecanumWheels(DriveTrain driveTrain, double width, double height, Units_length units)  {
        super(driveTrain, width, height, units);
        states = new ArrayList<FinalState>();
    }

    private void stopGradient() {}

    /**
     * @brief runs the command for the class to run the operation set by the addTrojectory method.
     * @return returns -1 if class isn't active; returns 0 on success.
     */
    @Override
    public int drive() {

        return 0;
    }

    /**
     * @brief Adds an operation to for the drive train to follow.
     * @param operation An instance of the Procedure class.
     * @return Returns 0 if success; returns -1 if class isn't active; returns 1 if procedure is invalid.
     */
    @Override
    public int addTrajectory(Procedure operation) {
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

    /**
     * @brief Adds a set of operations for the drive train to follow.
     * @param operation An instance of the Procedure class.
     * @return Returns 0 if success; returns -1 if class isn't active; returns 1 if a procedure is invalid.
     */
    public int addTrajectory(Procedure operation[]) {
        if(!super.active)
            return -1;
        for(Procedure i : operation) {
            int returnValue = addTrajectory(i);
            if (returnValue == -1)
                return -1;
            if(returnValue == 1)
                return 1;

        }
        return 0;
    }

    /**
     * @brief resets the list of operations for the drive train.
     * @return returns -1 if class isn't active.
     */
    @Override
    public int resetOperation() {
        if(super.resetOperation() == -1)
            return -1;

        this.states = new ArrayList<FinalState>();

        return 0;
    }

    /**
     * @brief Class used by PrecisionMecanumWheels to set the final states of the drive train.
     */
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
