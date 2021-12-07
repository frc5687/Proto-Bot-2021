package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.Intake;

public class RaiseIntake extends OutliersCommand{
    
    Intake intake;

    public RaiseIntake(Intake _intake){
        intake = _intake;
    }

    @Override
    public void initialize(){
        super.initialize();
        //Raise intake
        intake.intakeRaise();
    }

    @Override
    public boolean isFinished(){
        return super.isFinished();
    }
}
