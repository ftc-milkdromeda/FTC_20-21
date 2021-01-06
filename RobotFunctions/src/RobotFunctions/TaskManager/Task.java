package RobotFunctions.TaskManager;

public abstract class Task extends Thread{
    protected Task(Clock clock) {
        this.clock = clock;
    }

    public final void terminate() {
        this.deconstructor();
        this.interrupt();
    }
    protected void deconstructor() {}

    protected Clock clock;
}
