package RobotFunctions.TaskManager;

public abstract class KeyTask extends Task{
    protected KeyTask(Clock clock, Controller controller) {
        super(clock);
        this.controller = controller;
    }

    protected abstract double[] keyMapping();

    protected Controller controller;
}

//todo add documentation
