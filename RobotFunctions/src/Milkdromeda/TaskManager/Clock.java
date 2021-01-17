package Milkdromeda.TaskManager;

public class Clock extends Thread{
    public Clock(int refreshRate) {
        this.refreshRate = refreshRate;
        this.setPriority(this.priority);
    }

    @Override
    public synchronized void start() {
        if(!ThreadManager.attachClock(this))
            return;

        super.start();
    }

    @Override
    public void run() {
        while(!this.isInterrupted()) {
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - startTime < 1000 / refreshRate && !this.isInterrupted());
            currentClock++;
        }
    }

    public int getCurrentState() {
        return this.currentClock;
    }

    public double getCurrentRate() {
        long startTime = System.currentTimeMillis();
        int startClock = this.currentClock;

        int endClock = this.currentClock;
        long endTime = System.currentTimeMillis();
        while(endClock - startClock < 10) {
            if(endTime - startTime >= timeoutTimer)
                return -1;

            endClock = this.currentClock;
            endTime = System.currentTimeMillis();
        }

        return (double)(endClock - startClock) / ((double)(endTime - startTime) / 1000f);

    }

    protected final void terminate() {
        this.destructor();
        this.interrupt();
    }
    protected void destructor() {}

    private int refreshRate;
    private int currentClock;

    //constants
    private final int priority = 7;
    private final int timeoutTimer = 500;
}
