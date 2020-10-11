package RobotFunctions.Odometry;

public class DisplacmentOdometry extends Odometry {
    private DisplacmentOdometry() { this.point = new RobotState(0, 0, 0); }

    public static DisplacmentOdometry instance() {
        if (!Odometry.getReady())
            return null;
        return new DisplacmentOdometry();
    }

    public RobotState getNetDisplacment() {
        if(!operational)
            return null;

        return new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[VectorPosition.X.getValue()],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[VectorPosition.Y.getValue()],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());
    }

    public void setPoint() {
        if(!super.operational)
            return;

        this.point = new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[0],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[1],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());
    }
    public RobotState getDisplacment() {
        if(!operational)
            return null;

        return new RobotState(
                super.obj_initial.getCart()[0] + super.obj_travel.getCart()[VectorPosition.X.getValue()],
                super.obj_initial.getCart()[1] + super.obj_travel.getCart()[VectorPosition.Y.getValue()],
                super.obj_initial.getRotation() + super.obj_travel.getRotation());
    }

    private RobotState point;

}
