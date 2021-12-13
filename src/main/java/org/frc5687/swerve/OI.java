/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;
import edu.wpi.first.wpilibj.Joystick;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {
    protected Joystick translation;
    protected Joystick rotation;
    //Test

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        translation = new Joystick(0);
        rotation = new Joystick(5);
    }

    public void initializeButtons(DriveTrain driveTrain) {}

    public double getDriveY() {
        yIn = getSpeedFromAxis(translation, translation.getYChannel());
        //yIn = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        yIn = applyDeadband(yIn, Constants.DriveTrain.DEADBAND);
        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;  
    }

    public double getDriveX() {
        xIn = -getSpeedFromAxis(translation, translation.getXChannel());
        //xIn = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_X.getNumber());
        xIn = applyDeadband(xIn, Constants.DriveTrain.DEADBAND);
        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed = getSpeedFromAxis(rotation, rotation.getYChannel());
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return speed;
    }

    protected double getSpeedFromAxis(Joystick joystick, int axisNumber) {
        return joystick.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {}
}
