package RobotFunctions.GamePad;

import RobotFunctions.Error;

public abstract class KeyMappings {

    protected KeyMappings(Controller controller) {
        this.controller = controller;

        x = null;
        y = null;
        a = null;
        b = null;
        leftBumper = null;
        rightBumper = null;
        leftStickButton = null;
        rightStickButton = null;
        dpadUp = null;
        dpadDown = null;
        dpadLeft = null;
        dpadRight = null;
        rightStick = null;
        leftStick = null;
        leftTrigger = null;
        rightTrigger = null;
    }

    public synchronized Error x() {
        if(this.x != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.x = this.controller.get_X();
        return Error.NO_ERROR;
    }
    public synchronized Error y() {
        if(this.y != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.y = this.controller.get_Y();
        return Error.NO_ERROR;
    }
    public synchronized Error a() {
        if(this.a != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.a = this.controller.get_Y();
        return Error.NO_ERROR;
    }
    public synchronized Error b() {
        if(this.b != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.b = this.controller.get_B();
        return Error.NO_ERROR;
    }
    public synchronized Error leftBumper() {
        if(this.leftBumper != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.leftBumper = this.controller.get_LeftBumper();
        return Error.NO_ERROR;
    }
    public synchronized Error rightBumper() {
        if(this.rightBumper != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.rightBumper = this.controller.get_RightBumper();
        return Error.NO_ERROR;
    }
    public synchronized Error leftStickButton() {
        if(this.leftStickButton != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.leftStickButton = this.controller.get_LeftStickButton();
        return Error.NO_ERROR;
    }
    public synchronized Error rightStickButton() {
        if(this.rightStickButton != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.rightStickButton = this.controller.get_RightStickButton();
        return Error.NO_ERROR;
    }
    public synchronized Error dpadUp() {
        if(this.dpadUp != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.dpadUp = this.controller.get_DpadUp();
        return Error.NO_ERROR;
    }
    public synchronized Error dpadDown() {
        if(this.dpadDown != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.dpadDown = this.controller.get_DpadDown();
        return Error.NO_ERROR;
    }
    public synchronized Error dpadLeft() {
        if(this.dpadLeft != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.dpadLeft = this.controller.get_DpadLeft();
        return Error.NO_ERROR;
    }
    public synchronized Error dpadRight() {
        if(this.dpadRight != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.dpadRight = this.controller.get_DpadRight();
        return Error.NO_ERROR;
    }
    public synchronized Error rightStick() {
        if(this.rightStick != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.rightStick = new JoyStick();

        this.rightStick.X = this.controller.get_RightStick_X();
        this.rightStick.Y = this.controller.get_RightStick_Y();

        return Error.NO_ERROR;
    }
    public synchronized Error leftStick() {
        if(this.leftStick != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.leftStick = new JoyStick();

        this.leftStick.X = this.controller.get_LeftStick_X();
        this.leftStick.Y = this.controller.get_LeftStick_Y();

        return Error.NO_ERROR;
    }
    public synchronized Error leftTrigger() {
        if(this.leftTrigger != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.leftTrigger = this.controller.get_LeftTrigger();
        return Error.NO_ERROR;
    }
    public synchronized Error rightTrigger() {
        if(this.rightTrigger != null)
            return Error.KM_PREVIOUS_THREAD_ALREADY_STARTED;

        this.rightTrigger = this.controller.get_RightTrigger();
        return Error.NO_ERROR;
    }

    protected Boolean x;
    protected Boolean y;
    protected Boolean a;
    protected Boolean b;
    protected Boolean leftBumper;
    protected Boolean rightBumper;
    protected Boolean leftStickButton;
    protected Boolean rightStickButton;
    protected Boolean dpadUp;
    protected Boolean dpadDown;
    protected Boolean dpadLeft;
    protected Boolean dpadRight;
    protected JoyStick leftStick;
    protected JoyStick rightStick;
    protected Double leftTrigger;
    protected Double rightTrigger;

    protected Controller controller;
}

//todo add documentation
