package Drivers.Template;

import Drivers.DriverManager;
import TaskManager.Task;

public abstract class Driver {
    protected Driver() {
        this.interrupted = false;
        this.processId = -1;
        this.interruptId = -1;

        this.alive = true;
        this.busy = false;
    }

    public final boolean enterThread(Task task) {
        if(this.processId != -1)
            return false;

        this.processId = task.getProcessId();
        return true;
    }
    public final boolean exitThread(Task task) {
        if(task.getProcessId() == this.processId) {
            this.processId = -1;
            return true;
        }

        return false;
    }
    public final boolean interject(Task task) {
        if(this.interruptId != -1)
            return false;

        this.interruptId = task.getProcessId();
        this.interrupted = true;
        return true;
    }
    public final boolean returnMainThread(Task task) {
        if(task.getProcessId() == this.processId) {
            this.interruptId = -1;
            this.interrupted = false;
            return true;
        }

        return false;
    }

    public final void terminate() {
        this.destructor();
        this.alive = false;
    }
    protected void destructor() {}

    public boolean isAlive() {
        return this.alive;
    }

    protected final boolean testTask(Task task) {
        return (!this.interrupted && this.getProcessId() == this.processId || this.interrupted && task.getProcessId() == this.interruptId);
    }

    public int getProcessId() {
        return this.processId;
    }

    private int processId;
    private int interruptId;

    protected boolean busy;
    protected boolean interrupted;
    protected boolean alive;
}
