/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;
import org.frc5687.swerve.Constants.Maverick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {

    private Joystick leftJoystick;
    private Joystick rightJoystick;
    private JoystickButton MaverickBTN;
    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        leftJoystick = new Joystick(1);
        rightJoystick = new Joystick(2);

        MaverickBTN = new JoystickButton(leftJoystick, 1);
    }

    public void initializeButtons(DriveTrain driveTrain, Maverick maverick) {
        MaverickBTN.whenHeld(new org.frc5687.swerve.commands.Maverick(driveTrain));
    }

    public double getDriveY() {
        //        yIn = getSpeedFromAxis(_leftJoystick, _leftJoystick.getYChannel());
        yIn = applyDeadband(yIn, DEADBAND);

        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        //        xIn = -getSpeedFromAxis(_leftJoystick, _leftJoystick.getXChannel());
        xIn = applyDeadband(xIn, DEADBAND);

        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed = getSpeedFromAxis(rightJoystick, rightJoystick.getZChannel());
        speed = applyDeadband(speed, 0.2);
        return speed;
    }

    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {}
}
