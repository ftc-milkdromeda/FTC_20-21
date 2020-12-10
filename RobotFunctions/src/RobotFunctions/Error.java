package RobotFunctions;

public enum Error {
    CLASS_NOT_ACTIVE(-1),
    DIVISION_BY_ZERO(-2),
    ARGUMENTS_OUT_OF_BOUND(-3),
    INVALID_STATES(-4),

    //errors for MecanumWheels.MecanumWheels
    PROCESS_ALREADY_RUNNING(001),
    NO_PROCESS_RUNNING(002),
    NO_PROCEDURE_SET(003),

    //errors for MecanumWheels.Operation
    EXECUTION_STOPPED_FORCEFULLY(101),
    NO_TEMPLATE_PROVIDED(102),
    THREAD_NOT_STARTED(103),

    //errors for GamePad.KeyMappings
    KM_PREVIOUS_THREAD_ALREADY_STARTED(201),

    NO_ERROR(0);
    private int errorCode;
    private Error(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getValue() {
        return this.errorCode;
    }
}
