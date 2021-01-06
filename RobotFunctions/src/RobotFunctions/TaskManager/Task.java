package RobotFunctions.TaskManager;

public abstract class Task extends Thread{
    protected Task(Clock clock) {
        this.clock = clock;
        this.status = false;
    }

    @Override
    public abstract void run();

    public final void terminate() {
        this.deconstructor();
        this.interrupt();
    }
    protected void deconstructor() {}

    protected boolean status;
    protected Clock clock;
}
