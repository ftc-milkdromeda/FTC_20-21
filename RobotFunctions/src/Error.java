public enum Error {
    CLASS_NOT_ACTIVE(-1),
    NO_ERROR(0),
    DIVISION_BY_ZERO(-2),
    ARGUMENTS_OUT_OF_BOUND(-3),
    INVALID_STATES(-4),

    //errors for mecanum wheels
    INCORRECT_PROCEDURE_MODE(001),
    PROCEDURE_MODE_NOT_INIT(001);

    public int errorCode;
    private Error(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getValue() {
        return this.errorCode;
    }
}
