package Milkdromeda.Drivers;

import Milkdromeda.RobotFunctions.Units_time;
import Milkdromeda.RobotFunctions.MecanumWheels.Motor;

/**
 * @brief Class that runs operations; class operations run on separate thread.
 */
public abstract class DriveTrain{
    private void netSpeed() {

    }

    /**
     * @brief Sets the power for individual motors.
     * @param index The motor that is being set.
     * @param power The power in which the motor is set to.
     */
    public abstract void setMotor(Motor index, double power);
    public abstract int getEncoder(Motor index);

    /**
     * @brief Sets all the motor speeds on the drive train to 0.
     */
    public void stop() {
        this.setMotor(Motor.UPPER_RIGHT, 0);
        this.setMotor(Motor.LOWER_RIGHT, 0);
        this.setMotor(Motor.LOWER_LEFT, 0);
        this.setMotor(Motor.UPPER_LEFT, 0);
    }

    /**
     * @brief Sets the power to all the motors on the drive train.
     * @param power the power of the individual motors on the drive drain; motor index equal to array index.
     */
    public void setMotor(double[] power) {
        for(int a = 0; a < power.length; a++)
            setMotor(Motor.cast(a), power[a]);
    }

    public int[] getEncoder() {
        int returnArray[] = {
                this.getEncoder(Motor.cast(0)),
                this.getEncoder(Motor.cast(1)),
                this.getEncoder(Motor.cast(2)),
                this.getEncoder(Motor.cast(3)),
        };

        return returnArray;

    }
    public double getSpeed(Motor index, int testTime, Units_time units) {
        testTime *= units.getValue();

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        int startPosition = this.getEncoder(index);

        while(endTime - startTime <= testTime)
            endTime = System.currentTimeMillis();

        return (this.getEncoder(index) - startPosition) / (endTime - startTime);
    }
    public double[] getSpeed(int testTime, Units_time units) {
        testTime *= units.getValue();

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        int startPosition[] = { this.getEncoder(Motor.cast(0)),
                this.getEncoder(Motor.cast(1)),
                this.getEncoder(Motor.cast(2)),
                this.getEncoder(Motor.cast(3)) };


        while(endTime - startTime <= testTime)
            endTime = System.currentTimeMillis();

        double[] returnArray = new double[4];

        for(int a = 0; a < returnArray.length; a++)
            returnArray[a] = (this.getEncoder(Motor.cast(a)) - startPosition[a]) / (endTime - startTime);

        return returnArray;
    }
    public double getLength() {
        return this.length;
    }
    public double getWidth() {
        return this.width;
    }
    public double getWheelRadius() {
        return this.wheelRadius;
    }
    public double getTpr() {
        return this.tpr;
    }

    protected double length = -1;
    protected double width = -1;
    protected double tpr = -1; //ticks per radian
    protected double maxSpeed = -1; //radians per second
    protected double wheelRadius = -1;
}

