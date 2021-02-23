import Milkdromeda.Drivers.DriveTrain;
import Milkdromeda.RobotFunctions.MecanumWheels.MecanumWheels;
import Milkdromeda.RobotFunctions.MecanumWheels.Motor;
import Milkdromeda.RobotFunctions.MecanumWheels.Procedure;

public class MecanumTest {
    public static void main(String [] args) {
        DriveTrain drive = new PrintMotors();
        MecanumWheels calculator = new MecanumWheels(drive);

        calculator.addTrajectory(new Procedure(Math.PI / 2, 1, .5));
        calculator.drive();
        calculator.stop();
        calculator.hardStop();
    }

    public static class PrintMotors extends DriveTrain {
        public PrintMotors() {
            super.length = 10;
            super.width = 10;
            super.tpr = 100;
            super.wheelRadius = 3;
            super.maxSpeed = 52;
        }
        @Override
        public void setMotor(Motor index, double power) {
            System.out.println(index.getValue() + ": " + power);
        }

        @Override
        public int getEncoder(Motor index) {
            return 0;
        }
    }
}
