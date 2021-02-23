package Milkdromeda.RobotFunctions.MecanumWheels;

import Milkdromeda.RobotFunctions.Error;
import Milkdromeda.RobotFunctions.Units_length;
import Milkdromeda.RobotFunctions.Units_time;
import Milkdromeda.Drivers.DriveTrain;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;
import Milkdromeda.TaskManager.ThreadManager;
import org.ejml.data.DMatrix4;
import org.ejml.data.DMatrix4x4;
import org.ejml.dense.fixed.CommonOps_DDF4;

import com.sun.istack.internal.NotNull;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class MecanumWheels {
    private void maximizeVector(DMatrix4 vector) {
        double maxValue = Math.abs(vector.get(1, 0));
        for(int a = 1; a < 4; a++) {
            if(maxValue < Math.abs(vector.get(a, 0)))
                maxValue = Math.abs(vector.get(a, 0));
        }

        if(maxValue == 0)
            return;

        CommonOps_DDF4.scale(1 / maxValue, vector);
    }

    public MecanumWheels(DriveTrain driveConfig) {
        this.bound = BoundType.NONE;
        this.boundValue = -1;
        this.settings = new MotorSetting();
        this.instanceRunning = false;
        this.driveConfig = driveConfig;
        this.startTime = -1;
        this.endTime = -1;
        this.currentProcedure = null;

        this.netVelocity = new DMatrix4x4(
                1, 0, -Math.sqrt(Math.pow(this.driveConfig.getLength(), 2) + Math.pow(this.driveConfig.getWidth(), 2)), 0,
                0, 1, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        );
        this.matrix = new DMatrix4x4(
                1, 1, (this.driveConfig.getLength() + this.driveConfig.getWidth()) / -2, 0,
                -1, 1, (this.driveConfig.getLength() + this.driveConfig.getWidth()) / -2, 0,
                -1, 1,  (this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 0,
                1, 1,  (this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 1
        );

        this.setError(Error.NO_ERROR);
    }
    MecanumWheels(@NotNull MecanumWheels object) {
        this.bound = object.bound;
        this.boundValue = object.boundValue;
        this.settings = new MotorSetting();
        this.driveConfig = object.driveConfig;
        this.instanceRunning = false;
        this.startTime = -1;
        this.endTime = -1;

        this.netVelocity = new DMatrix4x4(
                1, 0, -Math.sqrt(Math.pow(this.driveConfig.getLength(), 2) + Math.pow(this.driveConfig.getWidth(), 2)), 0,
                0, 1, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        );
        this.matrix = new DMatrix4x4(
                1, 1, -(this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 0,
                -1, 1, -(this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 0,
                -1, 1,  (this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 0,
                1, 1,  (this.driveConfig.getLength() + this.driveConfig.getWidth()) / 2, 1
        );
        this.currentProcedure = null;

        this.setError(Error.NO_ERROR);
   }

   public void copyMotorSetting(@NotNull MecanumWheels object) {
        this.setError(Error.NO_ERROR);
        this.settings = new MotorSetting(this.settings);
        this.currentProcedure = object.currentProcedure;

        this.setError(Error.NO_ERROR);
   }

    public void addTrajectory(@NotNull Procedure procedure) {
        DMatrix4 inputVector = new DMatrix4(
                Math.cos(procedure.getAngle()) * procedure.getMagnitude(),
                Math.sin(procedure.getAngle()) * procedure.getMagnitude(),
                procedure.getPivot(),
                0);

        DMatrix4 outputVector = new DMatrix4(0, 0, 0, 0);

        CommonOps_DDF4.mult(this.matrix, inputVector, outputVector);
        CommonOps_DDF4.scale( 2 /(this.driveConfig.getLength() + this.driveConfig.getWidth()), outputVector);
        this.maximizeVector(outputVector);
        CommonOps_DDF4.scale(procedure.getMagnitude(), outputVector);

        this.settings.setMotor(outputVector);
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

    public double getRunTime(Units_time units) {
        if(this.bound == BoundType.NONE) {
            this.setError(Error.MW_NO_BOUND_SET);
            return -1;
        } else if (this.bound == BoundType.TIME) {
            this.setError(Error.NO_ERROR);
            return this.boundValue / units.getValue();
        }

        DMatrix4 settings = this.settings.getMotor();
        DMatrix4 velocity = new DMatrix4(0, 0, 0, 0);
        DMatrix4 netVelocity = new DMatrix4(0, 0, 0, 0);
        DMatrix4x4 i_matrix = new DMatrix4x4(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        );
        CommonOps_DDF4.invert(this.matrix, i_matrix);
        CommonOps_DDF4.scale(this.driveConfig.getWheelRadius(), settings);
        CommonOps_DDF4.mult(i_matrix, settings, velocity);
        CommonOps_DDF4.mult(this.netVelocity, velocity, netVelocity);

        double velocityMag = Math.sqrt(Math.pow(netVelocity.a1, 2) + Math.pow(netVelocity.a2, 2));

        this.setError(Error.NO_ERROR);
        return this.boundValue / velocityMag / units.getValue();
    }
    public double getRunDistance(Units_length units) {
        if(this.bound == BoundType.NONE) {
            this.setError(Error.MW_NO_BOUND_SET);
            return -1;
        } else if (this.bound == BoundType.DISTANCE) {
            this.setError(Error.NO_ERROR);
            return this.boundValue / units.getValue();
        }

        DMatrix4 settings = this.settings.getMotor();
        DMatrix4 velocity = new DMatrix4(0, 0, 0, 0);
        DMatrix4 netVelocity = new DMatrix4(0, 0, 0, 0);
        DMatrix4x4 i_matrix = new DMatrix4x4(
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
                );
        CommonOps_DDF4.invert(this.matrix, i_matrix);
        CommonOps_DDF4.scale(this.driveConfig.getWheelRadius(), settings);
        CommonOps_DDF4.mult(i_matrix, settings, velocity);
        CommonOps_DDF4.mult(this.netVelocity, velocity, netVelocity);

        double velocityMag = Math.sqrt(Math.pow(netVelocity.a1, 2) + Math.pow(netVelocity.a2, 2));

        this.setError(Error.NO_ERROR);
        return velocityMag * this.boundValue / units.getValue();
    }

    public Procedure getCurrentProcedure() {
        if(currentProcedure == null)
            this.setError(Error.MW_NO_PROCEDURE_SET);
        else
            this.setError(Error.NO_ERROR);
        return currentProcedure;
    }

    public synchronized void drive() {
        if(MecanumWheels.running) {
            this.setError(Error.MW_PROCESS_ALREADY_RUNNING);
            return;
        }

        switch (this.bound) {
            case DISTANCE:
                this.boundTask = new DistanceBound(null, boundValue, this);
                break;
            case TIME:
                this.boundTask = new TimeBound(null, boundValue, this);
                break;
            default:
                this.boundTask = null;
        }

        MecanumWheels.running = true;
        this.instanceRunning = true;
        this.startTime = System.currentTimeMillis();

        if(this.boundTask != null)
            this.boundTask.start();

        this.driveConfig.setMotor(this.settings.getMotorArray());

        this.setError(Error.NO_ERROR);
    }
    public synchronized void updateDrive() {
        if(!this.instanceRunning)
            this.setError(Error.MW_NO_PROCESS_RUNNING);

        this.driveConfig.setMotor(this.settings.getMotorArray());

        this.setError(Error.NO_ERROR);
    }
    public synchronized void stop() {
        //todo add stop types for more easy transition between different operations
        if(!this.instanceRunning) {
            this.setError(Error.MW_NO_PROCESS_RUNNING);
            return;
        }

        this.driveConfig.stop();

        this.instanceRunning = false;
        MecanumWheels.running = false;
        this.endTime = System.currentTimeMillis();

        this.setError(Error.NO_ERROR);
    }
    public synchronized void hardStop() {
        this.driveConfig.stop();

        if(this.instanceRunning)
            this.endTime = System.currentTimeMillis();

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
            this.motors = object.motors;
        }
        private MotorSetting() {}

        private void setMotor(DMatrix4 motors) {
            this.motors = motors;
        }
        private DMatrix4 getMotor() {
            return new DMatrix4(motors);
        }
        private double[] getMotorArray() {
            return new double[] {this.motors.a1, this.motors.a2, this.motors.a3, this.motors.a4};
        }

        private DMatrix4 motors;

    }
    private static class TimeBound extends Task {
        protected TimeBound(Clock clock, double time, MecanumWheels control) {
            super(clock);
            this.time = time;
            this.control = control;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            while(!super.isInterrupted() && System.currentTimeMillis() - startTime <= time);

            ThreadManager.stopProcess(super.getProcessId());
        }

        @Override
        protected void deconstructor() {
            this.control.stop();
        }

        private double time;
        private MecanumWheels control;
    }
    //todo add distance PID for distance bound. Distance bound out of commission for now.
    private static class DistanceBound extends Task {
        protected DistanceBound(Clock clock, double distance, MecanumWheels control) {
            super(clock);
        }

        @Override
        public void run() {
            ThreadManager.stopProcess(super.getProcessId());
        }
    }

    private static boolean running = false;
    static boolean isRunning() {
        return MecanumWheels.running;
    }
    private boolean instanceRunning = false;

    private BoundType bound;
    private double boundValue;
    private MecanumWheels.MotorSetting settings;
    private DriveTrain driveConfig;
    private long startTime;
    private long endTime;
    private Procedure currentProcedure;
    private Task boundTask;

    private DMatrix4x4 matrix;
    private DMatrix4x4 netVelocity;
}

