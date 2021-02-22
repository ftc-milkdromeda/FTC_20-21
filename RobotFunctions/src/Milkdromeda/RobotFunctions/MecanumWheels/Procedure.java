package Milkdromeda.RobotFunctions.MecanumWheels;

public class Procedure{
    public Procedure(double angle, double magnitude, double pivot) {}

    public double getAngle() {
        return this.angle;
    }
    public double getMagnitude() {
        return this.magnitude;
    }
    public double getPivot() {
        return this.pivot;
    }

    private double angle;
    private double magnitude;
    private double pivot;
}
