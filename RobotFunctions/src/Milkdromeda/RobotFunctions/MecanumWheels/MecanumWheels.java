package Milkdromeda.RobotFunctions.MecanumWheels;

import Milkdromeda.Drivers.DriveTrain;
import Milkdromeda.RobotFunctions.Units_length;
import java.util.ArrayList;

/**
 * @brief Controls mecanum wheels operations. Class is a state machine.
 * @author Tyler Wang
 */
public abstract class MecanumWheels {
    /**
     * @brief Constructs new MecanumWheel objectfs
     * @param driveTrain instance of a subclass of Drive class.
     * @param width The length between the two front wheels on the drive train.
     * @param height The length between the two side wheels on the drive train.
     * @param units Units used to make the measurements.
     */
    protected MecanumWheels(DriveTrain driveTrain, double width, double height, Units_length units) {
        this.driveTrain = driveTrain;
        this.width = width * units.getValue();
        this.height = height * units.getValue();
        this.operation = new ArrayList<DriveOperation>();
    }

    /**
     * @brief Calculates the motor power configuration for successful completion of the procedure provided.
     * @param operation The procedure for which function will calculate a drive command for.
     * @return DriveOperation for which class can use.
     */
    protected DriveOperation calculatePath(Procedure operation) {
        double wheels[] = new double[4];

        //calculating strafe
        //operation: sin(R + PI/4)
        double F_0 = Math.sin(operation.getAngle() + Math.PI / 4);
        double F_1 = Math.sin(operation.getAngle() - Math.PI / 4);

        wheels[Motor.UPPER_LEFT.getValue()] = F_0;
        wheels[Motor.LOWER_RIGHT.getValue()] = F_0;
        wheels[Motor.UPPER_RIGHT.getValue()] = F_1;
        wheels[Motor.LOWER_LEFT.getValue()] = F_1;

        //calculate pivot
        //TODO fix pivot calcuation.
        Pivot: //skips if pivot distance is less than infinity
        {
            double wheel_x = this.width / 2;
            double wheel_y = this.height / 2;
            double pivotDistance = this.pivotPointCalculation(operation.getPivot()) * Math.sqrt(Math.pow(wheel_x, 2) + Math.pow(wheel_y, 2));
            if(pivotDistance == this.INFINITY) //testing if pivot is less than 0
                break Pivot;
            else if(pivotDistance == 0) {
                wheels[0] = -1 * Math.signum(operation.getPivot());
                wheels[1] = -1 * Math.signum(operation.getPivot());
                wheels[2] = Math.signum(operation.getPivot());
                wheels[3] = Math.signum(operation.getPivot());
                break Pivot;
            }

            //operation: P_x = P_d * cos(R + pi/2)
            double pivot_x = pivotDistance * Math.cos((Math.PI / 2) + operation.getAngle());
            //operation: P_y = P_d * sin(R + pi/2)
            double pivot_y = pivotDistance * Math.sin((Math.PI / 2) + operation.getAngle());

            //operation: r = sqrt[ (W_x - C_x)^2 + (W_y - C_y)^2 ]
            wheels[Motor.UPPER_RIGHT.getValue()] *= Math.sqrt(Math.pow(wheel_x - pivot_x, 2) + Math.pow(wheel_y - pivot_y, 2));
            wheels[Motor.UPPER_LEFT.getValue()]*= Math.sqrt(Math.pow(-1 * wheel_x - pivot_x, 2) + Math.pow(wheel_y - pivot_y, 2));
            wheels[Motor.LOWER_LEFT.getValue()] *= Math.sqrt(Math.pow(-1 * wheel_x - pivot_x, 2) + Math.pow(-1 * wheel_y - pivot_y, 2));
            wheels[Motor.LOWER_RIGHT.getValue()] *= Math.sqrt(Math.pow(wheel_x - pivot_x, 2) + Math.pow(-1 * wheel_y - pivot_y, 2));
        }
        //normalizing range between -1.0 - 1.0
        double maxValue = Math.abs(wheels[0]);
        for(int a = 1; a < 4; a++)
            maxValue = Math.abs(maxValue) < Math.abs(wheels[a]) ? Math.abs(wheels[a]) : maxValue;

        for(int a = 0; a < 4; a++)
            //operation: W_m = (W_m / W_max) * M_net
            wheels[a] *= operation.getMagnitude() / maxValue;

        return new DriveOperation(wheels);
    }

    /**
     * @brief Deletes the current object so that a new instance can be created.
     */
    public void deleteObject() {
        active = false;
        MecanumWheels.isInstance = false;
    }

    /**
     * @brief Checks whether the current object is active.
     * @return Returns true if current object is still active; returns false if current object isn't active.
     */
    protected boolean checkActivity() { return this.active; }

    /**
     * @brief Calculates distance of the pivot point for a given pivot magnitude.
     * @param x The magnitude of pivot that needs to be converted to the distance of pivot point.
     * @return Distance from robot centroid to pivot measured in multiples of the robot radius.
     */
    protected double pivotPointCalculation(double x) {
        double a = 2.5;
        //funtion: a * x^-1 - a * sgn(x)
        if(x == 0)
            return this.INFINITY;
        return a / x -a * Math.signum(x);
    }

    /**
     * @brief runs the command for the class to run the operation set by the addTrojectory method.
     * @return returns -1 if class isn't active; returns 0 on success.
     */
    public abstract int drive();

    /**
     * @brief Adds an operation to for the drive train to follow.
     * @param operation An instance of the Procedure class.
     * @return Returns 0 if success; returns -1 if class isn't active; returns 1 if procedure is invalid.
     */
    public abstract int addTrajectory(Procedure operation);

    /**
     * @brief Turns off all of the motors on the drive train.
     * @return returns -1 if class isn't active; returns 0 on success.
     */
    public int stop() {
        if(!this.active)
            return -1;
        this.driveTrain.stop();

        return 0;
    }

    /**
     * @brief Resets the operation that the class will follow.
     * @return returns -1 if class isn't active; returns 0 on success.
     */
    public int resetOperation() {
        if(!this.active)
            return -1;
        this.operation = new ArrayList<DriveOperation>();
        return 0;
    }

    protected  boolean active = true;
    protected static boolean isInstance = false;
    protected DriveTrain driveTrain;
    protected double width;
    protected  double height;
    protected ArrayList<DriveOperation> operation;
    protected double INFINITY = Double.POSITIVE_INFINITY;
}
