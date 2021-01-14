package RobotFunctions.MotorCalibration;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

class PID {
    public PID() {}

    private double integrate(double error[], double interval) {
        double returnValue = 0;
        for(int a = 0; a < error.length - 1; a++) {
            double m = (error[a + 1] - error[a]) / interval;

            //formula: .5mx_1^2 + bx_1 - .5mx_0^2 + bx_0
            returnValue += .5 * m * error[a + 1] * error[a + 1] + error[a] - (.5 * m * error[a] * error[a] + error[a] * error[a]);
        }

        return returnValue;
    }
    private double maxVelocity(double velocity, double fraction) {
        return velocity / fraction;
    }

    //public boolean readPID(String name) {}
    //public boolean writePID(String name, boolean override) {}
    public void initNew(Vector3D PIDValues) {
        double array[] = { 1, 1, 1 };
        PIDValues = new Vector3D(array);
    }
    public void setPID(Vector3D PIDValues) {
        this.PIDValues = PIDValues;
    }
    public Vector3D getPIDValues() {
        return this.PIDValues;
    }

    public double runPID(double error[], double interval) {
        double input[] = {
                error[error.length - 1],
                (error[error.length -1] - error[error.length - 2]) / interval,
                this.integrate(error, interval)
        };

        return Vector3D.dotProduct(this.PIDValues, new Vector3D(input));
    }
    public Vector3D trainPID(double theorySpeeds[], double actualSpeeds[], double powerFractions[], double pid[]) {
        if(theorySpeeds.length != actualSpeeds.length || powerFractions.length != pid.length || theorySpeeds.length != pid.length) {
            System.out.println("RobotFunctions: Array lengths different.");
            return null;
        }

        double gradient[] = this.PIDValues.toArray();


        for(int a = 0; a < 3; a++) {
            gradient[a] = 0;
            for(int b = 1; b < theorySpeeds.length; b++) {
                double maxVelocity = this.maxVelocity(actualSpeeds[b], powerFractions[b]);
                double dev = theorySpeeds[b] / maxVelocity - actualSpeeds[b - 1] / maxVelocity - pid[b];

                gradient[a] += 2 * dev / theorySpeeds.length - 1 - gradient[a];
            }
        }

        return new Vector3D(gradient);
    }
    public void applyGradient(Vector3D gradient) {
        this.PIDValues.subtract((Vector<Euclidean3D>)gradient);
    }

    private Vector3D PIDValues;
}
