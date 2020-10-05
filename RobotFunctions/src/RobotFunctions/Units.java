package RobotFunctions;

public enum Units {
    IN(0.0254), MM(0.001), CM(0.01), M(1), FT(0.3048); //Value corresponds with conversion factor to meters

    private double value;
    private Units(double value) {
        this.value = value;
    }
    public double getValue() {
        return this.value;
    }
}
