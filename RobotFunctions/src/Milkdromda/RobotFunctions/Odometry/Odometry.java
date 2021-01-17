package Milkdromda.RobotFunctions.Odometry;

public abstract class Odometry {
    //object function initialization.
    public void resetOdometry() {
        this.obj_travel = new RobotState(0, 0, 0);
        this.obj_travel = new RobotState(0, 0, 0);
    }
    public void startOdometry() {
        if(this.operational)
            return;

        this.obj_initial = Odometry.state;
        this.operational = true;
    }
    public void pauseOdometry() {
        this.operational = false;
    }


    //constructor initialization.
    protected Odometry(Config config) {
        this.obj_initial = new RobotState(0, 0, 0);
        this.obj_travel = new RobotState(0, 0, 0);
    }

    //static function declaration.
    /**
     * @brief initializes the function class. Only to be called once.
     * @param state the initial position of the robot.
     * @return returns -1 if class already initialized; 0 if the function class is initialized.
     */
    public static int setIntialState(RobotState state) {
        if(ready)
            return -1;

        Odometry.state = state;
        Odometry.lastCalibratedPoint = state;
        Odometry.initialState = state;
        return 0;
    }

    /**
     * @brief Recalibrates the odometry with a more accurate postiton.
     * @param state The more accurate postion used to calibrate the odometry.
     * @return Returns -1 if class funtion isn't ready; returns 0 if successful.
     */
    public static int reCalibrate(RobotState state) {
        if(!ready)
            return -1;

        Odometry.state = state;
        Odometry.lastCalibratedPoint = state;

        return 0;
    }
    /**
     * @brief Used to get the current state of the robot
     * @return The current position as calculated with odometry.
     */
    public static RobotState getCurrentState() {
        if(!ready)
            return null;

        return Odometry.state; }
    /**
     * @brief Gets the robots net position change since last reset.
     * @return net position change since last reset.
     */
    public static RobotState getTotalDisplacment() {
        if(!ready)
            return null;

        return new RobotState(
                Odometry.state.getCart()[VectorPosition.X.getValue()] - Odometry.initialState.getCart()[VectorPosition.X.getValue()],
                Odometry.state.getCart()[VectorPosition.Y.getValue()] - Odometry.initialState.getCart()[VectorPosition.Y.getValue()],
                Odometry.state.getRotation() - Odometry.initialState.getRotation()
        );
    }
    /**
     * @brief Gets the absolute position.
     * @return absolute position of robot.
     */
    public static RobotState getAbsolutePosition() {
        if(!ready)
            return null;
        return Odometry.state;
    }

    /**
     * @brief Updates the Odometry.
     * @param state The change in the wheels since the last time Odometry was updated.
     * @param timestamp A timestamp for when the data was taken.
     * @return returns 0 if function succeeds; returns -1 if class function hasn't been initialized.
     */
    public static int addUpdateOdometry(OdometryState state, long timestamp) {
        if(!ready)
            return -1;
        

        return 0;
    }

    /**
     * @brief Funtions used by sub-classes to figure out whether the class funtion is ready.
     * @return true if the function is ready; false if the funtion if not.
     */
    protected static boolean getReady() {
        return Odometry.ready;
    }

    //static variable declaration
    /**
     * @brief only able to create objects of Odometry class after variable set to true.
     */
    private static boolean ready = false;
    /**
     * @brief current state of the class funtion.
     */
    private static RobotState state;
    /**
     * @brief The point where the Robot was last calibrated.
     */
    private static RobotState lastCalibratedPoint;

    /**
     * @brief A read only variable for the initial state of the robot.
     */
    private static RobotState initialState;

    //global variable declaration.
    protected boolean operational = false;
    protected RobotState obj_initial;
    protected RobotState obj_travel;
}
