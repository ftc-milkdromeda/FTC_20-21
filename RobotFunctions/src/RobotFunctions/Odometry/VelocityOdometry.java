package RobotFunctions.Odometry;

public class VelocityOdometry extends Odometry {
    private VelocityOdometry() {
        this.initTime = System.currentTimeMillis();
        this.pointTime = System.currentTimeMillis();
        this.point = new RobotState(0, 0, 0);
    }

    public static VelocityOdometry instance() {
        if (!Odometry.getReady())
            return null;
        return new VelocityOdometry();
    }

    public RobotState getNetVelocity() {
        if(!operational && System.currentTimeMillis() - initTime < 5)
            return null;

        RobotState currentPosition = new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[VectorPosition.X.getValue()],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[VectorPosition.Y.getValue()],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());

        double x_velocity = (currentPosition.getCart()[VectorPosition.X.getValue()] - super.obj_initial.getCart()[VectorPosition.X.getValue()]) / ((System.currentTimeMillis()  - this.initTime) * 1000);
        double y_velocity = (currentPosition.getCart()[VectorPosition.Y.getValue()] - super.obj_initial.getCart()[VectorPosition.Y.getValue()]) / ((System.currentTimeMillis()  - this.initTime) * 1000);
        double w_velocity = (currentPosition.getRotation() - super.obj_initial.getRotation()) / ((System.currentTimeMillis()  - this.initTime) * 1000);

        return new RobotState(x_velocity, y_velocity, w_velocity);
    }

    public void setPoint() {
        if(!super.operational)
            return;

        this.point = new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[0],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[1],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());
        this.pointTime = System.currentTimeMillis();
    }
    public RobotState getVelocity() {
        if(!operational && System.currentTimeMillis() - pointTime < 5)
            return null;

        RobotState currentPosition = new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[VectorPosition.X.getValue()],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[VectorPosition.Y.getValue()],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());

        double x_velocity = (currentPosition.getCart()[VectorPosition.X.getValue()] - this.point.getCart()[VectorPosition.X.getValue()]) / ((System.currentTimeMillis()  - pointTime) * 1000);
        double y_velocity = (currentPosition.getCart()[VectorPosition.Y.getValue()] - this.point.getCart()[VectorPosition.Y.getValue()]) / ((System.currentTimeMillis()  - pointTime) * 1000);
        double w_velocity = (currentPosition.getRotation() - this.point.getRotation()) / ((System.currentTimeMillis()  - pointTime) * 1000);

        return new RobotState(x_velocity, y_velocity, w_velocity);
    }

    private RobotState point;
    private long pointTime;
    private long initTime;

}
