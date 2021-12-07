package org.frc5687.swerve.subsystems;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.OutliersContainer;

import edu.wpi.first.wpilibj.Servo;

public class Intake extends OutliersSubsystem{

    private Servo servoIntake;

    public Intake(OutliersContainer container) {
        super(container);
        //Configure intake
        servoIntake = new Servo(RobotMap.PWM.INTAKE_SERVO);
    }

    public void intakeRaise(){
        //Raise intake
        servoIntake.setAngle(Constants.Intake.MAX_ANGLE_RAISED);
    }

    public void intakeLowered(){
        //Lower intake
        servoIntake.setAngle(Constants.Intake.MAX_ANGLE_RAISED);
    }

    public void disableIntake(){
        servoIntake.setDisabled();
    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub
        
    }
}
