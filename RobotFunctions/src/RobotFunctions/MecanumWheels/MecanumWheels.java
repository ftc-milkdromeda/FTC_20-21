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
        this.operation = new ArrayList<DriveOperation>();
    }

    protected DriveOperation calculatePath(Procedure operation) {
        double wheels[] = new double[4];

        //calculating strafe
        //operation: sin(R + PI/4)
        double F_0 = Math.sin(operation.getAngle() + Math.PI / 4);
        double F_1 = Math.sin(operation.getAngle() - Math.PI / 4);

        wheels[Moter.UPPER_LEFT.getValue()] = F_0;
        wheels[Moter.LOWER_RIGHT.getValue()] = F_0;
        wheels[Moter.UPPER_RIGHT.getValue()] = F_1;
        wheels[Moter.LOWER_LEFT.getValue()] = F_1;

        //calculate pivot
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
            wheels[Moter.UPPER_RIGHT.getValue()] *= Math.sqrt(Math.pow(wheel_x - pivot_x, 2) + Math.pow(wheel_y - pivot_y, 2));
            wheels[Moter.UPPER_LEFT.getValue()]*= Math.sqrt(Math.pow(-1 * wheel_x - pivot_x, 2) + Math.pow(wheel_y - pivot_y, 2));
            wheels[Moter.LOWER_LEFT.getValue()] *= Math.sqrt(Math.pow(-1 * wheel_x - pivot_x, 2) + Math.pow(-1 * wheel_y - pivot_y, 2));
            wheels[Moter.LOWER_RIGHT.getValue()] *= Math.sqrt(Math.pow(wheel_x - pivot_x, 2) + Math.pow(-1 * wheel_y - pivot_y, 2));
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

    public void deleteObject() {
        active = false;
        MecanumWheels.isInstance = false;
    }

    protected boolean checkActivity() { return this.active; }
    protected double pivotPointCalculation(double x) {
        double a = 2.5;
        //funtion: a * x^-1 - a * sgn(x)
        if(x == 0)
            return this.INFINITY;
        return a / x -a * Math.signum(x);
    }

    public abstract int drive();
    public abstract int addTrojectory(Procedure operation);

    public int stop() {
        if(!this.active)
            return -1;
        double power[] = { 0, 0, 0, 0 };
        this.drive.setMoters(power);

        return 0;
    }
    public int resetOperation() {
        if(!this.active)
            return -1;
        this.operation = new ArrayList<DriveOperation>();
        return 0;
    }

    protected  boolean active = true;
    protected static boolean isInstance = false;
    protected Drive drive;
    protected double width;
    protected  double height;
    protected ArrayList<DriveOperation> operation;
    protected double INFINITY = Double.POSITIVE_INFINITY;
}
