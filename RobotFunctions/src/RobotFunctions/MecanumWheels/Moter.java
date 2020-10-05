package RobotFunctions.MecanumWheels;

public enum Moter {
    UPPER_LEFT(0), LOWER_LEFT(1), UPPER_RIGHT(2), LOWER_RIGHT(3);
    private int index;
    private Moter(int index) { this.index = index; }
    public int getValue() { return this.index; }
}
