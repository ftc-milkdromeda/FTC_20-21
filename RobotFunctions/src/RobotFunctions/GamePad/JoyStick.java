package RobotFunctions.GamePad;

public class JoyStick {
    public JoyStick() {
        this.X = 0;
        this.Y = 0;
    }
    public double X;
    public double Y;

    public double getMagnitude() {
        return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
    }
    public double getAngle() {
        return Math.cos(this.X / this.getMagnitude()) * Math.signum(this.X);
    }
}
