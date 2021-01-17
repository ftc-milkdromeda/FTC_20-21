package Milkdromda.RobotFunctions.Odometry;

enum VectorPosition {
    X(0), Y(1);

    private int value;
    private VectorPosition(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
