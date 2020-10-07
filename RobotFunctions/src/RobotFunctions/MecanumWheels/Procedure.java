package RobotFunctions.MecanumWheels;

public class Procedure {
    public Procedure(double angle, double magnitude, double pivot) {
        this.angle = angle;
        this.magnitude = magnitude;
        this.pivot = pivot;
    }

    public double getAngle() { return angle; }
    public double getMagnitude() { return magnitude; }
    public double getPivot() { return pivot; }

    private double angle;
    private double magnitude;
    private double pivot;
}
