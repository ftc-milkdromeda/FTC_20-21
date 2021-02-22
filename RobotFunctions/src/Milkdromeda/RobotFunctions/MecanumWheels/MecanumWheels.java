package Milkdromeda.RobotFunctions.MecanumWheels;

import RobotFunctions.Error;
import RobotFunctions.Units_length;
import RobotFunctions.Units_time;
import org.jetbrains.annotations.NotNull;

public class MecanumWheels {
    private MotorSetting normalizePower(MotorSetting settings) {
        //todo add and test formula for normalizing motor power.
    }
    private double getPivotDistance(double pivot) {
        double a = 2.5;
        if(pivot == 0)
            return Double.POSITIVE_INFINITY;
        return a * 1 / pivot + a * Math.signum(pivot);
    }

    MecanumWheels(Drive driveConfig) {
        this.bound = BoundType.NONE;
        this.boundValue = -1;
        this.normalized = false;
        this.settings = new MotorSetting();
        this.isPathUpdated = false;
        this.instanceRunning = false;
        this.driveConfig = driveConfig;
        this.startTime = -1;
        this.endTime = -1;
        this.isPathUpdated = true;
        this.currentProcedure = null;

        this.setError(Error.NO_ERROR);
    }
    MecanumWheels(@NotNull MecanumWheels object) {
        this.bound = object.bound;
        this.boundValue = object.boundValue;
        this.normalized = object.normalized;
        this.settings = new MotorSetting();
        this.isPathUpdated = false;
        this.driveConfig = object.driveConfig;
        this.instanceRunning = false;
        this.startTime = -1;
        this.endTime = -1;
        this.isPathUpdated = true;
        this.currentProcedure = null;

        this.setError(Error.NO_ERROR);
   }

   public void copyMotorSetting(@NotNull MecanumWheels object) {
        this.setError(Error.NO_ERROR);
        this.settings = new MotorSetting(this.settings);
        this.isPathUpdated = true;
        this.currentProcedure = object.currentProcedure;

        this.setError(Error.NO_ERROR);
   }

    //todo fill in add Trajectory
    public void addTrajectory(Procedure procedure) {
        double F_0 = Math.sin(procedure.getAngle() + Math.PI / 4);
        double F_1 = Math.sin(procedure.getAngle() - Math.PI / 4);
    }
    public void setNormalized(boolean normalized) {
        if(this.normalized == normalized)
            return;
        this.normalized = normalized;
        this.isPathUpdated = false;

        this.setError(Error.NO_ERROR);
    }
    public void setDistanceBounds(double distance, @NotNull Units_length units) {
        this.bound = BoundType.DISTANCE;
        this.boundValue = distance * units.getValue();

        this.setError(Error.NO_ERROR);
    }
    public void setTimeBounds(double time, @NotNull Units_time units) {
        this.bound = BoundType.TIME;
        this.boundValue = time * units.getValue();

        this.setError(Error.NO_ERROR);
    }
    public void deleteTrajectory() {
        this.settings = new MotorSetting();

        this.setError(Error.NO_ERROR);
    }

    public BoundType getBoundsType() {
        this.setError(Error.NO_ERROR);

        return this.bound;
    }
    public boolean getNormalized() {
        this.setError(Error.NO_ERROR);

        return this.normalized;
    }

    //todo fill in getRunTime
    public double getRunTime(Units_time units) {
        this.setError(Error.NO_ERROR);
    }
    //todo fill in getRunDistance.
    public double getRunDistance(Units_length units) {
        this.setError(Error.NO_ERROR);
    }

    public Procedure getCurrentProcedure() {
        if(currentProcedure == null)
            this.setError(Error.MW_NO_PROCEDURE_SET);
        else
            this.setError(Error.NO_ERROR);
        return currentProcedure;
    }

    public void drive() {
        if(MecanumWheels.running) {
            this.setError(Error.MW_PROCESS_ALREADY_RUNNING);
            return;
        }
        if(!this.isPathUpdated)
            this.addTrajectory(this.currentProcedure);

        MecanumWheels.running = true;
        this.instanceRunning = true;

        this.startTime = System.currentTimeMillis();
        this.driveConfig.setMotor(this.settings.getMotor());

        this.setError(Error.NO_ERROR);
    }
    public void updateDrive() {
        if(!this.instanceRunning)
            this.setError(Error.MW_NO_PROCESS_RUNNING);

        if(!this.isPathUpdated)
            this.addTrajectory(this.currentProcedure);

        this.driveConfig.setMotor(this.settings.getMotor());

        this.setError(Error.NO_ERROR);
    }
    public void stop() {
        //todo add stop types for more easy transition between different operations
        if(!this.instanceRunning) {
            this.setError(Error.MW_NO_PROCESS_RUNNING);
            return;
        }

        this.driveConfig.stop();

        this.instanceRunning = false;
        MecanumWheels.running = false;

        this.setError(Error.NO_ERROR);
    }
    public void hardStop() {
        this.driveConfig.stop();
        this.setError(Error.NO_ERROR);
    }
    public double getRuntime(Units_time units) {
        if(startTime == -1) {
            this.setError(Error.MW_NO_PROCESS_RUNNING);
            return 0;
        }
        this.setError(Error.NO_ERROR);

        if(endTime == -1)
            return (System.currentTimeMillis() - startTime) / (1000f * units.getValue());

        return (endTime - startTime) / (1000f * units.getValue());
    }

    private void setError(Error error) {
        this.lastError = error;
    }
    public Error getError() {
        return this.lastError;
    }
    private Error lastError = Error.NO_ERROR;

    private static class MotorSetting {
        private MotorSetting(MotorSetting object) {
            for(byte a = 0; a < motors.length; a++)
                this.motors[a] = object.motors[a];
        }
        private MotorSetting() {}

        private void setMotor(double power, Motor index) {
            this.motors[index.getValue()] = power;
        }

        private double[] getMotor() {
            double returnArray[] = new double[this.motors.length];

            for(int a = 0; a < this.motors.length; a++)
                returnArray[a] = this.motors[a];

            return returnArray;
        }
        private double motors[];
    }

    private static boolean running = false;
    static boolean isRunning() {
        return MecanumWheels.running;
    }
    private boolean instanceRunning = false;

    private BoundType bound;
    private double boundValue;
    private boolean normalized;
    private MecanumWheels.MotorSetting settings;
    private boolean isPathUpdated;
    private Drive driveConfig;
    private long startTime;
    private long endTime;
    private Procedure currentProcedure;
}

//todo need to add a closed-loop feedback system with wheel encoders.