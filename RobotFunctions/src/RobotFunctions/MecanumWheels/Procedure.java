package RobotFunctions.MecanumWheels;

import RobotFunctions.Units;

import java.util.Vector;

public class Procedure{
    public Procedure(double angle, double magnitude, double pivot) {
        mode = Mode.VECTOR;

        vector = new double[3];
        vector[0] = angle;
        vector[1] = magnitude;
        vector[2] = pivot;

    }
    public Procedure(double x, double y, double w, double magnitude, Units units) {
        mode = Mode.POINT;

        point = new double[3];

        point[0] = x * units.getValue();
        point[1] = y * units.getValue();
        point[2] = w;
        point[3] = magnitude;
    }

    public double getAngle() {
        if(mode != Mode.VECTOR)
            return 0;
        return vector[0];
    }
    public double getPivot() {
        if(mode != Mode.VECTOR)
            return 0;
        return vector[2];
    }

    public double getX() {
        if(mode != Mode.POINT)
        return 0;
        return this.point[0];
    }
    public double getY() {
        if(mode != Mode.POINT)
        return 0;
        return point[1];
    }
    public double getW() {
        if(mode != Mode.POINT)
        return 0;
        return point[2];
    }

    public double getMagnitude() {
        if(mode == Mode.POINT)
            return point[3];
        return vector[1];
    }

    public boolean isVectorMode() {
        return mode == Mode.VECTOR;
    }
    public boolean isPointMode() {
        return mode == Mode.POINT;
    }

    private enum Mode {
        VECTOR, POINT;
    }

    private double[] vector;
    private double[] point;
    private Mode mode;
}
