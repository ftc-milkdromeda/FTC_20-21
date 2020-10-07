package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

public class RoughMecanumWheels extends MecanumWheels{
    public static RoughMecanumWheels instance(Drive drive, double width, double height, Units units) {
        if(MecanumWheels.isInstance)
            return null;
        return new RoughMecanumWheels(drive, width, height, units);
    }
    private RoughMecanumWheels(Drive drive, double width, double height, Units units) {
        super(drive, width, height, units);
    }

    public int drive() {
        if(operation == null)
            return -1;
        super.drive.setMoters(operation.getMoters());
        return 0;
    }

    public void resetOperation() { this.operation = null; }

    public void setTrojectory(Procedure operation) {
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
        Pivot: //skips if pivot distance is less than 0
        {
            double wheel_x = super.width / 2;
            double wheel_y = super.height / 2;
            double pivotDistance = super.pivotPointCalculation(operation.getPivot()) * Math.sqrt(Math.pow(wheel_x, 2) + Math.pow(wheel_y, 2));
            if(pivotDistance < 0) //testing if pivot is less than 0
                break Pivot;
            else if(pivotDistance == 0) {
                wheels[0] = -1 * Math.signum(operation.getAngle());
                wheels[1] = -1 * Math.signum(operation.getAngle());
                wheels[2] = Math.signum(operation.getAngle());
                wheels[3] = Math.signum(operation.getAngle());
                break Pivot;
            }

            //operation: P_x = P_d * cos(R + pi/2)
            double pivot_x = pivotDistance * Math.cos(operation.getAngle() + Math.PI / 2 * Math.signum(pivotDistance));
            //operation: P_y = P_d * sin(R + pi/2)
            double pivot_y = pivotDistance * Math.sin(operation.getAngle() + Math.PI / 2);

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

        this.operation = new DriveOperation(wheels);
    }

    DriveOperation operation = null;
}
