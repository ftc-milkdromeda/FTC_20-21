package RobotFunctions.MecanumWheels;

class DriveOperation {
    DriveOperation(double moter[]) { this.moter = moter; }

    public double getMoter(Moter index) { return this.moter[index.getValue()]; }
    public double[] getMoters() { return this.moter; }

    private double moter[];
}
