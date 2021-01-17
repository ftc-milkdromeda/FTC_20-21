package Milkdromeda.Drivers;

import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.TaskManager.JoyStick;

/**
 * @brief Converts gamepad inputs from FTC controller to Milkdromda.RobotFunctions' controller.
 */
public abstract class Controller extends Driver {
    public abstract double get_X();
    public abstract double get_Y();
    public abstract double get_A();
    public abstract double get_B();
    public abstract double get_LeftBumper();
    public abstract double get_RightBumper();
    public abstract double get_LeftStickButton();
    public abstract double get_RightStickButton();
    public abstract double get_DpadUp();
    public abstract double get_DpadDown();
    public abstract double get_DpadLeft();
    public abstract double get_DpadRight();
    public abstract JoyStick get_LeftStick();
    public abstract JoyStick get_RightStick();
    public abstract double get_LeftTrigger();
    public abstract double get_RightTrigger();
    public abstract double get_Back();
}
