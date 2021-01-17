package Milkdromda.TaskManager;

import Milkdromda.Drivers.Controller;

public abstract class JoyStickTask extends KeyTask {
    protected JoyStickTask(Clock clock, Controller controller){
        super(clock, controller);
    }

    protected abstract JoyStick[] joyStickMapping();

    @Override
    protected double[] keyMapping() {
        return null;
    }
}
