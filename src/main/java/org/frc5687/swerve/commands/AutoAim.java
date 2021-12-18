package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.Limelight;

public class AutoAim extends OutliersCommand{

    DriveTrain driveTrain;
    Limelight limelight;
    
    public AutoAim(DriveTrain _driveTrain){
        driveTrain = _driveTrain;
    }

    private void autoAim(){
        if(limelight.hasTarget()){

        }
    }

    @Override
    public void initialize(){
        super.initialize();
    }
    
    @Override
    public void execute(){
        super.execute();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
