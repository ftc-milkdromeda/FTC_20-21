package Milkdromeda.TaskManager;

public class JoyStick {
    public JoyStick() {
        this.X = 0;
        this.Y = 0;
    }

    public JoyStick(double x, double y) {
        this.X = x;
        this.Y = y;
    }
    public double X;
    public double Y;

    public double getMagnitude() {
        return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
    }
    public double getAngle() {
        double angle = Math.acos(this.X / this.getMagnitude()) * Math.signum(this.Y);
        if(angle == 0)
            return this.X >= 0 ? 0.0 : Math.PI;
        return Math.acos(this.X / this.getMagnitude()) * Math.signum(this.Y);
    }
}
