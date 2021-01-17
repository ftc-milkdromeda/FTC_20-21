package Milkdromeda.Drivers;

import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.Image.Bitmap;
import Milkdromeda.TaskManager.Task;

public abstract class RobotCamera extends Driver {
    public abstract Bitmap takeImage(Task task);
}
