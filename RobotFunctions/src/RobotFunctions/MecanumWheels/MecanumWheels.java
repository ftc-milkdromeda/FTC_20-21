package RobotFunctions.MecanumWheels;

import RobotFunctions.Units_length;
import RobotFunctions.Units_time;
import RobotFunctions.Error;

public class MecanumWheels {
    MecanumWheels(Drive driveConfig) {
        this.bound = BoundType.NONE;
        this.boundValue = -1;
        this.normalized = false;
        this.settings = new MotorSetting();
        this.isPathUpdated = false;
        this.instanceRunning = false;
        this.driveConfig = driveConfig;
        this.startTime = 0;

        this.setError(Error.NO_ERROR);
    }

    MecanumWheels(MecanumWheels object) {
        this.bound = object.bound;
        this.boundValue = object.boundValue;
        this.normalized = object.normalized;
        this.settings = new MotorSetting();
        this.isPathUpdated = false;
        this.driveConfig = object.driveConfig;
        this.instanceRunning = false;
        this.startTime = 0;

        this.setError(Error.NO_ERROR);
   }

   public void copyMotorSetting(MecanumWheels object) {
        this.setError(Error.NO_ERROR);
        this.settings = new MotorSetting(this.settings);
        this.isPathUpdated = true;

        this.setError(Error.NO_ERROR);
   }

    //todo fill in add Trajectory
    public void addTrajectory(Procedure procedure) {}
    public void setNormalized(boolean normalized) {
        if(this.normalized == normalized)
            return;
        this.normalized = normalized;
        this.isPathUpdated = false;

        this.setError(Error.NO_ERROR);
    }
    public void setDistanceBounds(double distance, Units_length units) {
        this.bound = BoundType.DISTANCE;
        this.boundValue = distance * units.getValue();

        this.setError(Error.NO_ERROR);
    }
    public void setTimeBounds(double time, Units_time units) {
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

    void drive() {
        if(MecanumWheels.running) {
            this.setError(Error.PROCESS_ALREADY_RUNNING);
            return;
        }

        MecanumWheels.running = true;
        this.instanceRunning = true;

        this.startTime = System.currentTimeMillis();
        this.driveConfig.setMotor(this.settings.getMotor());

        this.setError(Error.NO_ERROR);
    }
    void updateDrive() {
        if(!this.instanceRunning)
            this.setError(Error.NO_PROCESS_RUNNING);

        this.driveConfig.setMotor(this.settings.getMotor());

        this.setError(Error.NO_ERROR);
    }
    long stop() {
        if(!this.instanceRunning) {
            this.setError(Error.NO_PROCESS_RUNNING);
            return -1;
        }

        this.driveConfig.stop();

        this.instanceRunning = false;
        MecanumWheels.running = false;

        this.setError(Error.NO_ERROR);

        return System.currentTimeMillis() - startTime;
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
}
