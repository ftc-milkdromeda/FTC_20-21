public enum Error {
    CLASS_NOT_ACTIVE(-1), NO_ERROR(0), DIVISION_BY_ZERO(1), ARGUMENTS_OUT_OF_BOUND(2), INVALID_STATES(3);

    public int errorCode;
    private Error(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getValue() {
        return this.errorCode;
    }
}
