package Milkdromeda.Drivers;

import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.Image.Image;
import Milkdromeda.TaskManager.Task;

public abstract class RobotCamera extends Driver {
    public abstract Image takeImage(Task task);
}
