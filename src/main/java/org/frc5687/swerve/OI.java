/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;

import org.frc5687.swerve.commands.Drive;
import org.frc5687.swerve.commands.Maverick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.Gamepad;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {

    protected Gamepad gamepad;
    protected Joystick translation;
    protected Joystick rotation;
    protected Button _driverRightStickButton;
    private JoystickButton _trigger;
    private JoystickButton maverickBTN;
    private Button _driverRightTrigger;
    private Maverick maverick;
    private JoystickButton restNavX;

    private DriveTrain driveTrain;
    //Test

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        gamepad = new Gamepad(5);
        translation = new Joystick(0); //Translation
        rotation = new Joystick(1); //Rotation
        maverickBTN = new JoystickButton(translation, 4);
        restNavX = new JoystickButton(translation, 5);
        initializeButtons(driveTrain);
    }

    public void initializeButtons(DriveTrain driveTrain) {
        maverickBTN.whenActive(new Maverick(driveTrain));
    }

    public double getDriveY() {
        yIn = getSpeedFromAxis(translation, translation.getYChannel());
        //yIn = getSpeedFromAxis(gamepad, Gamepad.Axes.LEFT_X.getNumber());
        yIn = applyDeadband(yIn, DEADBAND);
        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        xIn = -getSpeedFromAxis(translation, translation.getXChannel());
        //xIn = -getSpeedFromAxis(gamepad, Gamepad.Axes.LEFT_Y.getNumber());
        xIn = applyDeadband(xIn, DEADBAND);
        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed;
        speed = getSpeedFromAxis(rotation, rotation.getXChannel());
        //speed = getSpeedFromAxis(gamepad, Gamepad.Axes.RIGHT_X.getNumber());
        speed = applyDeadband(speed, Constants.DriveTrain.ROTATION_DEADBAND);
        return speed;
        
    }

    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Y raw values", yIn);
        SmartDashboard.putNumber("X raw values", xIn);
    }
}
