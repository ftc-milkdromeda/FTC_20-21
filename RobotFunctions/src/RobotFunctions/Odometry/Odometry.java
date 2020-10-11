package RobotFunctions.Odometry;

import java.awt.*;

public abstract class Odometry {
    public void resetOdometry() {
        this.obj_travel = new RobotState(0, 0, 0);
        this.obj_travel = new RobotState(0, 0, 0);
    }
    public void startOdometry() {
        if(this.operational)
            return;

        this.obj_initial = Odometry.currentState;
        this.operational = true;
    }
    public void pauseOdometry() {
        this.operational = false;
    }

    protected Odometry() {
        this.obj_initial = new RobotState(0, 0, 0);
        this.obj_travel = new RobotState(0, 0, 0);
    }

    public static int reCalibrate(RobotState state) {
        if(!ready)
            return -1;
        Odometry.currentState = state;

        return 0;
    }
    public static int setIntialState(RobotState state) {
        if(ready)
            return -1;

        Odometry.initialState = state;
        return 0;
    }
    public static RobotState getCurrentState() { return Odometry.currentState; }

    public static void setCurrentState(OdometryState state) {
        //TODO add odometry calcuation
    }
    public static RobotState getNetPosition() { return Odometry.currentState; }

    protected static boolean getReady() { return Odometry.ready; }

    private static boolean ready = false;
    private static RobotState initialState;
    private static RobotState currentState;

    protected boolean operational = false;
    protected RobotState obj_initial;
    protected RobotState obj_travel;
}
