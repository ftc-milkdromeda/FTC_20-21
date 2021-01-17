package Milkdromda.RobotFunctions;

public enum Units_time {
    S(1.0), MS(1.0 / 1000), MUS(1.0 / 1000000.0), MIN(60);

    private double value;
    private Units_time(double value) {
        this.value = value;
    }
    public double getValue() {
        return this.value;
    }
}
