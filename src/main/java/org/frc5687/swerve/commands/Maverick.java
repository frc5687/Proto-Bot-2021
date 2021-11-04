package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class Maverick {
    
    private Pose2d destination;
    private DriveTrain driveTrain;

    public Maverick(DriveTrain DriveTrain){
        driveTrain = DriveTrain;
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
    
}
