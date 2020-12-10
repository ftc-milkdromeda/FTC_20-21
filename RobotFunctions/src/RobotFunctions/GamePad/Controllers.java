package RobotFunctions.GamePad;

/**
 * @brief Converts gamepad inputs from FTC controller to RobotFunctions' controller.
 */
public abstract class Controllers {
    public abstract boolean get_X(GamePadID gamepad);
    public abstract boolean get_Y(GamePadID gamepad);
    public abstract boolean get_A(GamePadID gamepad);
    public abstract boolean get_B(GamePadID gamepad);
    public abstract boolean get_LeftBumper(GamePadID gamepad);
    public abstract boolean get_RightBumper(GamePadID gamepad);
    public abstract boolean get_LeftStickButton(GamePadID gamepad);
    public abstract boolean get_RightStickButton(GamePadID gamepad);
    public abstract boolean get_DpadUp(GamePadID gamepad);
    public abstract boolean get_DpadDown(GamePadID gamepad);
    public abstract boolean get_DpadLeft(GamePadID gamepad);
    public abstract boolean get_DpadRight(GamePadID gamepad);
    public abstract double get_RightStick(GamePadID gamepad);
    public abstract double get_LeftStick(GamePadID gamepad);
    public abstract double get_LeftTrigger(GamePadID gamepad);
    public abstract double get_RightTrigger(GamePadID gamepad);
}
