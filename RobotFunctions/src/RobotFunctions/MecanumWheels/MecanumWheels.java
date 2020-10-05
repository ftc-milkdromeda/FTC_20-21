package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;
import java.util.ArrayList;

/**
 * @brief Controls mecanum wheels operations. Class is a state machine.
 * @author Tyler Wang
 */
public class MecanumWheels {

    /**
     * @brief Used to create MecanumWheels instances.
     * @param wheelConfDiagnal the length between two diagnal wheels.
     * @param units Units used in for the wheelConfDiagnal
     * @param drive an subclass of the Drive class to be used to drive the trolly
     * @return returns new instance; returns null if instance already exist.
     */
    public static MecanumWheels instance(double wheelConfDiagnal, Units units, Drive drive) {
        if(isInstance)
            return null;
        return new MecanumWheels(wheelConfDiagnal, units, drive);
    }

    public void run() {

    }

    /**
     * @brief Constructor for the MecanumWheels class.
     *  @param wheelConfDiagnal the length between two diagnal wheels.
     *  @param units Units used in for the wheelConfDiagnal
     *  @param drive an subclass of the Drive class to be used to drive the trolly
     *  @return returns new instance; returns null if instance already exist.
     */
    private MecanumWheels(double wheelConfDiagnal, Units units, Drive drive) {
        this.wheelConfDiagnal = wheelConfDiagnal * units.getValue();
        this.drive = drive;
    }

    public void setProcedure(Procedure operation) {
        //setting up the strafing action
        double f_0s = Math.sin(operation.getAngle() + Math.PI / 4) / 2;
        double f_1s = Math.sin(operation.getAngle() - Math.PI/4) / 2;

        //setting up the pivot action
        double F_1p = operation.getPivot() * -1;
        double F_2p = operation.getPivot();

        //mixing operations
        double moters[4];
        moters[0] = f_0s
        this.operation = new DriveOperation();
    }

    public static boolean isInstance = false;

    private double wheelConfDiagnal;
    private DriveOperation operation = null;
    private Drive drive;
}
