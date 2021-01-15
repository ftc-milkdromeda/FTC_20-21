package RobotFunctions.MotorCalibration;

import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.*;
import java.util.Scanner;

public class PID {
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

    public boolean readPID(String name) {
        name = name + ".pid";

        File file = new File(name);
        Scanner input;

        try {
            input = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            return false;
        }

        double value[] = new double[3];

        try {
            for (int a = 0; a < value.length; a++)
                value[a] = input.nextDouble();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        this.PIDValues = new Vector3D(value);

        return true;
    }
    public boolean writePID(String name, boolean override) {
        name = name + ".pid";

        File file = new File(name);

        try {
            if(!file.createNewFile()) {
                if (override) {
                    file.delete();
                    file.createNewFile();
                }
                else
                    return false;
            }
        }
        catch (IOException e) {
            return false;
        }

        try {
            FileWriter output = new FileWriter(name);
            output.write("" + PIDValues.toArray()[0] + " "  + PIDValues.toArray()[1] +  " " + PIDValues.toArray()[2]);
            output.close();
        }
        catch (IOException e) {
            return false;
        }

        return true;
    }
    public void initNew() {
        double array[] = { 1, 1, 1 };
        PIDValues = new Vector3D(array);
    }
    public void setPID(double PIDValues[]) {
        this.PIDValues = new Vector3D(PIDValues) ;
    }
    public double[] getPIDValues() {
        return this.PIDValues.toArray();
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
    public void applyGradient(Vector3D gradient, double rate) {
        this.PIDValues.subtract(rate, (Vector<Euclidean3D>)gradient);
    }

    private Vector3D PIDValues;
}
