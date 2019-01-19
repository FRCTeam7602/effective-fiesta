package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Simple sample arcade drive type robot based on WPI and Phoenix samples.
 */
public class Robot extends TimedRobot {
    private Joystick joystick = new Joystick(0);
    private VictorSPX leftMotor = new VictorSPX(1);
    private VictorSPX rightMotor = new VictorSPX(2);

    @Override
    public void robotInit() {
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);
    }

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
        double side = filterDeadband(joystick.getX());
        leftMotor.set(ControlMode.PercentOutput, (forward - side) / 2);
        rightMotor.set(ControlMode.PercentOutput, (forward + side) / 2);
    }

    /**
     * This came from CTRE example - I like it because the joystick will
     * often cause the bot to creep when it should just be sitting.
     */
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