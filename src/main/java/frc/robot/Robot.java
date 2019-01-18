package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Simple sample arcade drive type robot based on WPI and Phoenix samples.
 */
public class Robot extends TimedRobot {
    private VictorSPX leftMotor = new VictorSPX(1);
    private VictorSPX rightMotor = new VictorSPX(2);
    private Joystick joystick = new Joystick(0);

    @Override
    public void teleopInit() {
        leftMotor.set(ControlMode.PercentOutput, 0);
        leftMotor.setInverted(true);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void teleopPeriodic() {
        // joystick Y is negative when pushed away
        double forward = filterDeadband(-1 * joystick.getY());
        double turn = filterDeadband(joystick.getTwist());
        leftMotor.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, turn);
        rightMotor.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
    }

    private double filterDeadband(double value) {
        if (value >= 0.05) {
            return value;
        }
        if (value <= -0.05) {
            return value;
        }
        return value;
    }
}