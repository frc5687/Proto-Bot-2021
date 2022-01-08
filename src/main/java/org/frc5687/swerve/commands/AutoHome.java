package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class AutoHome extends OutliersCommand{
    
    private DriveTrain driveTrain;
    private Maverick maverick;
    private Pose2d homePose;
    private Rotation2d homeRot;

    public AutoHome(DriveTrain _driveTrain){
        driveTrain = _driveTrain;
        maverick = new Maverick(driveTrain);
        homeRot = new Rotation2d(0.0);
        homePose = new Pose2d(0, 0, homeRot);
    }

    @Override
    public void execute(){
        super.execute();
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public boolean isFinished(){
        return driveTrain.MaverickDone(homePose);
    }
}
