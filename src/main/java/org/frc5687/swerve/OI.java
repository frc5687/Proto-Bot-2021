/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;
import org.frc5687.swerve.Constants.Maverick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.Helpers;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {

    private Joystick translation;
    private Joystick rotation;
    private JoystickButton MaverickBTN;
    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        translation = new Joystick(5); //Left joystick
        rotation = new Joystick(2); // Right Joystick

        MaverickBTN = new JoystickButton(translation, 1);
    }

    public void initializeButtons(DriveTrain driveTrain, Maverick maverick) {
        MaverickBTN.whenHeld(new org.frc5687.swerve.commands.Maverick(driveTrain));
    }

    public double getDriveY() {
        yIn = getSpeedFromAxis(translation, translation.getYChannel());
        //        yIn = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        yIn = Helpers.applyDeadband(yIn, DEADBAND);

        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        xIn = -getSpeedFromAxis(translation, translation.getXChannel());
        xIn = Helpers.applyDeadband(xIn, DEADBAND);

        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed = getSpeedFromAxis(rotation, rotation.getXChannel());
        speed = Helpers.applyDeadband(speed, 0.2);
        return speed;
    }

    protected double getSpeedFromAxis(Joystick joystick, int axisNumber) {
        return joystick.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {}
}
