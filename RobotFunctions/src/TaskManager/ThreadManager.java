package TaskManager;

import java.util.ArrayList;

public class ThreadManager {
    private ThreadManager() {}

    public static void stopProcess(int index) {
        ThreadManager.threads.get(index).terminate();
        ThreadManager.threads.set(index, null);

        ThreadManager.numOfThreads--;
    }
    public static void stopClock() {
        if(ThreadManager.clock != null) {
            ThreadManager.clock.terminate();
            ThreadManager.clock = null;
            ThreadManager.numOfThreads--;
        }
    }
    public static int attachProcess(Task thread) {
        ThreadManager.threads.add(thread);

        ThreadManager.numOfThreads++;

        return ThreadManager.threads.size() - 1;
    }
    public static boolean attachClock(Clock clock) {
        if(ThreadManager.clock == null) {
            ThreadManager.clock = clock;
            numOfThreads++;
            return true;
        }
        return false;
    }

    public static void stopAllProcess() {
        for(Task thread : ThreadManager.threads)
            if(thread != null)
                thread.terminate();

        if(ThreadManager.clock != null)
            ThreadManager.clock.terminate();

        ThreadManager.threads = new ArrayList<Task>();
        ThreadManager.numOfThreads = 0;
        ThreadManager.clock = null;
    }

    public static int numOfProcess() {
        return ThreadManager.numOfThreads;
    }

    private static ArrayList<Task> threads = new ArrayList<Task>();
    private static Clock clock = null;
    private static int numOfThreads = 0;
}
