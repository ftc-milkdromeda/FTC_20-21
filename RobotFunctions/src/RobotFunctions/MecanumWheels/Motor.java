package RobotFunctions.MecanumWheels;

public enum Motor {
    UPPER_LEFT(0), LOWER_LEFT(1), UPPER_RIGHT(2), LOWER_RIGHT(3);
    private int index;
    private Motor(int index) { this.index = index; }
    public int getValue() { return this.index; }

    public static Motor cast(int value) {
        switch (value) {
            case 0:
                return UPPER_LEFT;
            case 1:
                return LOWER_LEFT;
            case 2:
                return UPPER_RIGHT;
            case 3:
                return LOWER_RIGHT;
            default:
                return null;
        }
    }
}
