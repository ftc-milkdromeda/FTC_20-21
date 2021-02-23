package Milkdromeda.RobotFunctions.MecanumWheels;

import Milkdromeda.RobotFunctions.Error;
import Milkdromeda.RobotFunctions.Units_time;
import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class Operation extends Task {
    public Operation(Clock clock) {
        super(clock);
        this.template = null;
        this.queue = new ArrayList<MecanumWheels>();
        this.template = null;
        this.isRunning = false;
        this.cursor = -1;
        this.startPosition = 0;
        this.interrupt = Interrupt.NONE;
        this.startTime = -1;
        this.endTime = -1;
        this.object = null;
    }
    public Operation(Clock clock, MecanumWheels template) {
        super(clock);
        this.template = template;
        this.queue = new ArrayList<MecanumWheels>();
        this.template = null;
        this.isRunning = false;
        this.cursor = -1;
        this.startPosition = 0;
        this.interrupt = Interrupt.NONE;
        this.startTime = -1;
        this.endTime = -1;
        this.object = null;
    }
    public Operation(@NotNull Operation o) {
        super(o.clock);
        this.template = o.template;
        this.queue = o.queue;
        this.template = o.template;
        this.isRunning = false;
        this.cursor = -1;
        this.startPosition = o.startPosition;
        this.interrupt = Interrupt.NONE;
        this.startTime = -1;
        this.endTime = -1;
        this.object = null;
    }

    public void setCursor(int index) {
        if(index < -1 || index > this.queue.size()) {
            this.setError(Error.ARGUMENTS_OUT_OF_BOUND);
            return;
        }

        this.cursor = index;

        this.setError(Error.NO_ERROR);
    }
    public int getCursorPosition() {
        this.setError(Error.NO_ERROR);
        return this.cursor;
    }

    public void add() {
        if(this.template == null) {
            this.setError(Error.O_NO_TEMPLATE_PROVIDED);
            return;
        }
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        if(this.cursor == -1)
            this.queue.add(new MecanumWheels(this.template));
        else
            this.queue.set(this.cursor, new MecanumWheels(this.template));

        this.setError(Error.NO_ERROR);
    }
    public void add(MecanumWheels o) {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        if(this.cursor == -1)
            this.queue.add(o);
        else
            this.queue.set(cursor, o);

        this.setError(Error.NO_ERROR);
    }
    public void remove() {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        if(this.cursor == -1)
            this.queue.remove(this.queue.size() - 1);

        this.queue.remove(this.cursor);

        this.setError(Error.NO_ERROR);
    }
    public void removeAfter() {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        if(cursor != -1)
            for(int a = cursor + 1; a < queue.size(); a++)
                this.queue.remove(this.cursor + 1);

        this.setError(Error.NO_ERROR);
    }
    public void removeBefore() {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        if(cursor != -1)
            for(int a = 0; a < this.cursor; a++)
                this.queue.remove(0);

        this.setError(Error.NO_ERROR);
    }
    public void setStart(int startPosition) {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        this.startPosition = startPosition;

        this.setError(Error.NO_ERROR);
    }

    @Override
    public synchronized void start() {
        if(this.isRunning) {
            this.setError(Error.O_THREAD_ALREADY_STARTED);
            return;
        }

        this.startTime = System.currentTimeMillis();
        this.isRunning = true;

        super.start();
    }
    @Override
    public void run() {
        for(int a = this.startPosition; a < this.queue.size(); a++) {
            this.cursor = a;
            this.object = this.queue.get(a);

            this.object.drive();
            while(this.interrupt != Interrupt.NEXT || this.object.isRunning())
                if(this.interrupt == Interrupt.STOP) {
                    this.object.stop();
                    this.object = null;
                    this.setError(Error.O_EXECUTION_STOPPED_FORCEFULLY);
                    return;
                }

            this.object.stop();
        }

        this.setError(Error.NO_ERROR);
    }
    public void updateCurrent() {
        this.object.updateDrive();
        this.setError(Error.NO_ERROR);
    }
    public void next() {
        this.interrupt = Interrupt.NEXT;
        this.setError(Error.NO_ERROR);
    }
    public void end() {
        if(!this.isRunning) {
            this.setError(Error.O_THREAD_NOT_STARTED);
            return;
        }

        this.interrupt = Interrupt.STOP;
        this.setError(Error.NO_ERROR);
        this.isRunning = false;
        this.object.hardStop();
        this.object = null;
        this.cursor = -2;
    }
    public double getRuntime(Units_time units) {
        if(startTime == -1) {
            this.setError(Error.O_THREAD_NOT_STARTED);
            return 0;
        }
        this.setError(Error.NO_ERROR);

        if(endTime == -1)
            return (System.currentTimeMillis() - startTime) / (1000f * units.getValue());

        return (endTime - startTime) / (1000f * units.getValue());
    }

    private Error lastError;
    public Error getError() {
        return this.lastError;
    }
    private void setError(Error lastError) {
        this.lastError = lastError;
    }

    private enum Interrupt {
        STOP, NEXT, UPDATE, NONE;
    }

    public boolean isRunning() {
        this.setError(Error.NO_ERROR);

        return this.isRunning;
    }

    private ArrayList<MecanumWheels> queue;
    private MecanumWheels template;
    private boolean isRunning;
    private int cursor;
    private int startPosition;
    private Operation.Interrupt interrupt;
    private long startTime;
    private long endTime;

    public MecanumWheels object;
}

//todo need to add a closed loop feedback system for with odometry.
//todo need to utilize error handling provided by MecanumWheels.