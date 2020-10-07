import RobotFunctions.MecanumWheels.Drive;
import RobotFunctions.MecanumWheels.Moter;
import RobotFunctions.MecanumWheels.Procedure;
import RobotFunctions.MecanumWheels.RoughMecanumWheels;
import RobotFunctions.Units;

public class Test {
    public static void main(String [] args) {
        Drive print = new Print();
        RoughMecanumWheels wheels = RoughMecanumWheels.instance(print, 18, 18, Units.IN);

        Procedure operation = new Procedure(Math.PI * .25, 1.0, 0);
        wheels.setTrojectory(operation);
        wheels.drive();
    }
}

class Print extends Drive {
    @Override
    public void setMoter(Moter index, double power) {
        System.out.println(index.getValue() + ": " + (float)power);
    }
    @Override
    public void setMoters(double[] powers) {
        for(int a = 0; a < 4; a++)
            System.out.println(a + ": " + (float)powers[a]);
    }
}
