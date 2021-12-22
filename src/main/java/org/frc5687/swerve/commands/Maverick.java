package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.DriveTrain;  
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;


public class Maverick extends OutliersCommand{
    private Pose2d destnation;
    private DriveTrain driveTrain;
    

    /**
     * Maverick State-Space Model (which may not be added)
     * Properties
     * States: [Meters Pre Second]
     * Input: [Distance to target]
     * Output: [Velocity]
     * @param _driveTrain
     */

    public Maverick(DriveTrain _driveTrain){
        driveTrain = _driveTrain;
        addRequirements(driveTrain);
    }

    public void Afterburner(){
        //Pushes the drive train up to 4.0 mps
        //The theoretical max mps is 5.5
        Constants.DriveTrain.MAX_MPS = 4.0;
    }

    public double getVelocityTheta(double vx, double vy){
        //The fraction would look like vx/vy
        return Math.atan(vy/vx);
    }

    public double getTheAngluarVelocityVector(double vx, double vy){
        //Using the pythagorean theorem where a^2 is the X velocity 
        //b^2 is the Y velocity
        //Thus c^2 is the angular movement velocity
        //Square root of X velocity squared added to Y velocity squared
        return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    public boolean getCheckPoints(int x1, int y1, int x2, int y2, int x, int y){
        //Is the point inside of the rectangle 
        //Double check the math
        if(x > x2 && x < x2 && y > y1 && y > y2){
            //Inside of the rectangle
            System.out.println("Maverick: " + true);
            //блин!!
            return true;
        }
        else{
            //Not inside of the rectangle
            System.out.println("Maverick: " + false);
            return false;
        }
    }

    public void wayPointMove(){
        //Iterate through all of the waypoints
        metric("MAVERICK", "Running");
        for(int i = 0; i < Constants.Maverick.numberOfWaypoints; i++){
            metric("MAVERICK", "At waypoint: " + i);
            //Create translations and rotations based off of the Maverick presets
            Translation2d move = new Translation2d(Constants.Maverick.waypointsX[i], Constants.Maverick.waypointsY[i]);
            Rotation2d rotation = new Rotation2d(Constants.Maverick.rotations[i]);
            destnation = new Pose2d(move, rotation);
            //Update the speeds with the realivent Maverick speed
            Constants.DriveTrain.MAX_MPS = Constants.Maverick.speeds[i];
            //Move the robot
            driveTrain.poseFollower(destnation, rotation, getTheAngluarVelocityVector(driveTrain.getXVelocity(), driveTrain.getYVelocity()));
        }
        metric("MAVERICK", "Move(s) Complete");
    }

    @Override
    public void initialize() {
        super.initialize();
        metric("MAVERICK", "Init");
        wayPointMove();
    }

    @Override public void execute(){
        super.execute();
    }
    
    @Override
    public boolean isFinished(){
        super.isFinished();
        //Is the robot at it's end position
        metric("MAVERICK", "Finished");
        return driveTrain.MaverickDone(destnation);
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
    }
}