/* Team 5687 (C)2021 */
package org.frc5687.swerve.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import org.frc5687.swerve.subsystems.DriveTrain;

public class DriveTrajectory extends OutliersCommand {
    //Create an drivetrain, trajectory, timer
    private final DriveTrain _driveTrain;
    private Trajectory _trajectory;
    private final Timer _timer;

    public DriveTrajectory(DriveTrain driveTrain, Trajectory trajectory) {
        _driveTrain = driveTrain;
        _timer = new Timer();
        _trajectory = trajectory;
        addRequirements(_driveTrain);
    }

    @Override
    public void initialize() {
        //Runs once on start comand
        super.initialize();
        _timer.reset();
        _timer.start();
    }

    @Override
    public void execute() {
        //Excuted every 20ms
        super.execute();
        Trajectory.State goal = _trajectory.sample(_timer.get());
        _driveTrain.trajectoryFollower(goal, new Rotation2d(0.0));
    }

    @Override
    public boolean isFinished() {
        //Checks to see if the comand is done
        return _timer.get() >= _trajectory.getTotalTimeSeconds();
    }

    @Override
    public void end(boolean interrupted) {
        //Runs at the end of the comand
        super.end(interrupted);
        _timer.reset();
    }

}
