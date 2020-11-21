package RobotFunctions.GamePad;

public enum GamePadID {
    GAMEPAD1(0), GAMEPAD2(1);

    private int id;

    private GamePadID(int id) {
        this.id = id;
    }

    public int getValue() {
        return this.id;
    }
}
