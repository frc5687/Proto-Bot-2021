/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import edu.wpi.first.wpilibj.Joystick;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.Helpers;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {
    private Joystick translate;
    private Joystick rotation;
    //Test

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        translate = new Joystick(1);
        rotation = new Joystick(0);
    }

    public void initializeButtons(DriveTrain driveTrain) {}

    public double getDriveY() {
        yIn = getSpeedFromAxis(translate, translate.getYChannel());
        yIn = Helpers.applyDeadband(yIn, Constants.DriveTrain.DEADBAND);
        metric("Translate Y Channel", translate.getYChannel());
        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        xIn = -getSpeedFromAxis(translate, translate.getXChannel());
        xIn = Helpers.applyDeadband(xIn, Constants.DriveTrain.DEADBAND);
        metric("Translate X Channel", translate.getXChannel());
        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        return rotation.getX();
    }

    protected double getSpeedFromAxis(Joystick joystick, int axisNumber) {
        metric("Axis channel", axisNumber);
        metric("Axis", joystick.getRawAxis(axisNumber));
        return joystick.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {}
}
