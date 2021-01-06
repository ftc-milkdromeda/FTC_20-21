package RobotFunctions.TaskManager;

/**
 * @brief Converts gamepad inputs from FTC controller to RobotFunctions' controller.
 */
public interface Controller {
    double get_X();
    double get_Y();
    double get_A();
    double get_B();
    double get_LeftBumper();
    double get_RightBumper();
    double get_LeftStickButton();
    double get_RightStickButton();
    double get_DpadUp();
    double get_DpadDown();
    double get_DpadLeft();
    double get_DpadRight();
    JoyStick get_LeftStick();
    JoyStick get_RightStick();
    double get_LeftTrigger();
    double get_RightTrigger();
}
