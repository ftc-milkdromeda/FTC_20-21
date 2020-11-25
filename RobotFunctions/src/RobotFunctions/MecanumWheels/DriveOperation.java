package RobotFunctions.MecanumWheels;

class DriveOperation {
    /**
     * @brief Creates a new DriveOperation instance.
     * @param motor An array of motor power indexed according to motor index.
     */
    DriveOperation(double motor[]) { this.motor = motor; }

    /**
     * @brief Returns the motor at given index.
     * @param index The index of the motor to be returned.
     * @return A double between -1.0 and 1.0 of the power of the motor.
     */
    public double getMotor(Motor index) { return this.motor[index.getValue()]; }

    /**
     * @brief Returns all 4 motors on the drive train indexed according to motor index.
     * @return A new array of all the motor powers on the drive train.
     */
    public double[] getMotors() {
        double returnValue[] = new double[4];
        for(int a = 0; a < motor.length; a++)
            returnValue[a] = this.motor[a];

        return returnValue;
    }

    private double motor[];
}
