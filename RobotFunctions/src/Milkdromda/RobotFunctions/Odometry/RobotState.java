package Milkdromda.RobotFunctions.Odometry;

public class RobotState {
    public RobotState(double x, double y, double w) {
        this.componentVector[0] = x;
        this.componentVector[1] = y;
        this.rotation = w;
    }

    public double[] getPolar() {return null;}
    public double[] getCart() {return null;}
    public double getRotation() {return 0;}

    private double[] componentVector;
    private double rotation;
}
