/* (C)2021 */
package org.frc5687.infiniterecharge.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc5687.infiniterecharge.robot.Constants;
import org.frc5687.infiniterecharge.robot.subsystems.DriveTrain;
import org.frc5687.infiniterecharge.robot.subsystems.Hood;
import org.frc5687.infiniterecharge.robot.subsystems.Shooter;

public class AutoTarget extends OutliersCommand {

    private DriveTrain _drivetrain;
    private Shooter _shooter;
    private Hood _hood;

    public AutoTarget(DriveTrain drivetrain, Shooter shooter, Hood hood) {
        _drivetrain = drivetrain;
        _shooter = shooter;
        _hood = hood;
        addRequirements(_shooter, _hood);
    }

    @Override
    public void initialize() {
        _drivetrain.setUseAutoAim(true);
    }

    @Override
    public void execute() {
        super.execute();
        _hood.setPosition(65);
        _shooter.setVelocitySpeed(5000);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        _drivetrain.setUseAutoAim(false);
        _hood.setPosition(Constants.Hood.MIN_ANGLE);
        Command hoodCommand = _hood.getDefaultCommand();
        if (hoodCommand instanceof IdleHood) {
            ((IdleHood) hoodCommand).setZeroing(true);
        }
    }
}
