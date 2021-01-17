package Milkdromda.TaskManager;

public abstract class Task extends Thread{
    protected Task(Clock clock) {
        this.clock = clock;
    }

    @Override
    public synchronized void start() {
        this.processId  = ThreadManager.attachProcess(this);

        super.start();
    }
    public final int getProcessId() {
        return this.processId;
    }
    final void terminate() {
        this.deconstructor();
        this.interrupt();
    }
    protected void deconstructor() {}

    private int processId;
    protected Clock clock;
}
