package Milkdromda.Drivers;

import Milkdromda.Drivers.Template.Driver;
import Milkdromda.Image.Image;
import Milkdromda.TaskManager.Task;

public abstract class RobotCamera extends Driver {
    public abstract Image takeImage(Task task);
}
