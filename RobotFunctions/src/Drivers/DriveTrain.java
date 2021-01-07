package Drivers;

import Drivers.Template.Driver;
import RobotFunctions.MecanumWheels.Motor;
import RobotFunctions.Odometry.Odometry;
import RobotFunctions.Odometry.RobotState;
import RobotFunctions.Units_length;

/**
 * @brief Class that runs operations; class operations run on separate thread.
 */
public abstract class DriveTrain extends Driver {
    private void netSpeed() {

    }

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
     * @brief Sets the power for individual motors.
     * @param index The motor that is being set.
     * @param power The power in which the motor is set to.
     */
    public abstract void setMotor(Motor index, double power);

    /**
     * @brief Sets the power to all the motors on the drive train.
     * @param power the power of the individual motors on the drive drain; motor index equal to array index.
     */
    public abstract void setMotors(double[] power);

    /**
     * @brief Gives speed for all the motors on the robot; gives the motor speed in meters per second.
     * @param encoder An array that will accept the output of the function.
     */
    public abstract void getMotorSpeed(double[] encoder);

    /**
     * @brief Gives the speed of a specific motor in meters per second.
     * @return The encoder information for the specific motor.
     */
    public abstract double getMotorSpeed();
}

