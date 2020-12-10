package RobotFunctions.GamePad;

/**
 * @brief Converts gamepad inputs from FTC controller to RobotFunctions' controller.
 */
public interface Controller {
    boolean get_X();
    boolean get_Y();
    boolean get_A();
    boolean get_B();
    boolean get_LeftBumper();
    boolean get_RightBumper();
    boolean get_LeftStickButton();
    boolean get_RightStickButton();
    boolean get_DpadUp();
    boolean get_DpadDown();
    boolean get_DpadLeft();
    boolean get_DpadRight();
    double get_RightStick_X();
    double get_LeftStick_X();
    double get_RightStick_Y();
    double get_LeftStick_Y();
    double get_LeftTrigger();
    double get_RightTrigger();
}
