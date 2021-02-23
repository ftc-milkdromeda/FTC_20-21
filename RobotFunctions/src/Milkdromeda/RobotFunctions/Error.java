package Milkdromeda.RobotFunctions;

public enum Error {
    //errors for MecanumWheels.MecanumWheels
    MW_PROCESS_ALREADY_RUNNING(001),
    MW_NO_PROCESS_RUNNING(002),
    MW_NO_PROCEDURE_SET(003),
    MW_NO_BOUND_SET(004),

    //errors for MecanumWheels.Operation
    O_EXECUTION_STOPPED_FORCEFULLY(101),
    O_NO_TEMPLATE_PROVIDED(102),
    O_THREAD_NOT_STARTED(103),
    O_THREAD_ALREADY_STARTED(104),

    //errors for GamePad.KeyMappings
    KM_PREVIOUS_THREAD_ALREADY_STARTED(201),

    CLASS_NOT_ACTIVE(-1),
    DIVISION_BY_ZERO(-2),
    ARGUMENTS_OUT_OF_BOUND(-3),
    INVALID_STATES(-4),
    NO_ERROR(0);
    private int errorCode;
    private Error(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getValue() {
        return this.errorCode;
    }
}
