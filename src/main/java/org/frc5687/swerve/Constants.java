/* Team 5687 (C)2020-2021 */
/**
 * 
 */
package org.frc5687.swerve;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.util.Units;

public class Constants{
        public static final int TICKS_PER_UPDATE = 1;
        public static final double METRIC_FLUSH_PERIOD = 1.0;
        public static final double UPDATE_PERIOD = 0.02;
        public static final double EPSILON = 0.00001;
        public static final double DELTA = 1e-2; // Execptable error for unit testing
        public static class DriveTrain {
    
            public static final double WIDTH = 0.6223;
            public static final double LENGTH = 0.6223;
            public static final Translation2d FRONT_LEFT_POSITION =
                    new Translation2d(WIDTH / 2.0, LENGTH / 2.0);
            public static final double FRONT_LEFT_ENCODER_OFFSET = Math.PI + Math.PI; // radians
            public static final Translation2d FRONT_RIGHT_POSITION =
                    new Translation2d(WIDTH / 2.0, -LENGTH / 2.0);
            public static final double FRONT_RIGHT_ENCODER_OFFSET = Math.PI + Math.PI; // radians
            public static final Translation2d BACK_LEFT_POSITION =
                    new Translation2d(-WIDTH / 2.0, LENGTH / 2.0);
            public static final double BACK_RIGHT_ENCODER_OFFSET = Math.PI + Math.PI + 0.007; // radians
            public static final Translation2d BACK_RIGHT_POSITION =
                    new Translation2d(-WIDTH / 2.0, -LENGTH / 2.0);
            public static final double BACK_LEFT_ENCODER_OFFSET = Math.PI; // radians
    
            public static final double DEADBAND = 0.2;
            public static final double ROTATION_DEADBAND = 0.2;
            public static final double ROTATION_SPEED = 0.5;
    
            public static double MAX_MPS = 1.5; // Max speed of robot (m/s) .
    
            public static final double MAX_ANG_VEL = Math.PI * 2.0; // Max rotation rate of robot (rads/s)
            public static final double MAX_MPSS = 0.5; // Max acceleration of robot (m/s^2)
    
            public static final double ANGLE_kP = 3.5;
            public static final double ANGLE_kI = 0.0;
            public static final double ANGLE_kD = 0.0;
    
            public static final double kP = 10.5;
            public static final double kI = 0.0;
            public static final double kD = 0.5;
            public static final double PROFILE_CONSTRAINT_VEL = 3.0 * Math.PI;
            public static final double PROFILE_CONSTRAINT_ACCEL = Math.PI;
        }
    
        public static class DifferentialSwerveModule {
    
            // update rate of our modules 5ms.
            public static final double kDt = 0.005;
    
            public static final double FALCON_FREE_SPEED =
                    Units.rotationsPerMinuteToRadiansPerSecond(6380);
            public static final int TIMEOUT = 200;
            public static final double GEAR_RATIO_WHEEL = 6.46875;
            public static final double GEAR_RATIO_STEER = 9.2;
            public static final double FALCON_RATE = 600.0;
            public static final double WHEEL_RADIUS = 0.0429; // Meters with compression.
            public static final double TICKS_TO_ROTATIONS = 2048.0;
            public static final double VOLTAGE = 12.0;
            public static final double FEED_FORWARD = VOLTAGE / (FALCON_FREE_SPEED / GEAR_RATIO_WHEEL);
    
            public static final boolean ENABLE_CURRENT_LIMIT = true;
            public static final double CURRENT_LIMIT = 30.0;
            public static final double CURRENT_THRESHOLD = 30.0;
            public static final double CURRENT_TRIGGER_TIME = 0.0;
    
            // Create Parameters for DiffSwerve State Space
            public static final double INERTIA_WHEEL = 0.005;
            public static final double INERTIA_STEER = 0.004;
            // A weight for how aggressive each state should be ie. 0.08 radians will try to control the
            // angle more aggressively than the wheel angular velocity.
            public static final double Q_AZIMUTH_ANG_VELOCITY = 1.1; // radians per sec
            public static final double Q_AZIMUTH = 0.08; // radians
            public static final double Q_WHEEL_ANG_VELOCITY = 5; // radians per sec
            // This is for Kalman filter which isn't used for azimuth angle due to angle wrapping.
            // Model noise are assuming that our model isn't as accurate as our sensors.
            public static final double MODEL_AZIMUTH_ANGLE_NOISE = .1; // radians
            public static final double MODEL_AZIMUTH_ANG_VELOCITY_NOISE = 5.0; // radians per sec
            public static final double MODEL_WHEEL_ANG_VELOCITY_NOISE = 5.0; // radians per sec
            // Noise from sensors. Falcon With Gearbox causes us to have more uncertainty so we increase
            // the noise.
            public static final double SENSOR_AZIMUTH_ANGLE_NOISE = 0.01; // radians
            public static final double SENSOR_AZIMUTH_ANG_VELOCITY_NOISE = 0.1; // radians per sec
            public static final double SENSOR_WHEEL_ANG_VELOCITY_NOISE = 0.1; // radians per sec
            public static final double CONTROL_EFFORT = VOLTAGE;
        } 
        public static class Maverick{
            //Constants for controling Maverick
            public static short numberOfWaypoints = 1; // Dropped down from four so Maverick doesn't overrun the position
            public static double[] waypointsX = {0.0, 0.0, 0.0, 0.0};
            public static double[] waypointsY = {0.0, 0.0, 0.0, 0.0};
            public static double[] rotations = {0.0, 0.0, 0.0, 0.0};
            public static double[] tolerences = {0.0, 0.0, 0.0, 0.0};
            public static double[] speeds = {3.5, 3.5, 3.5, 3.5, 3.5};
            public static boolean[] afterburner = {false, false, false, false};
            public static double[] MAGIC_NUMBERS = {0.0, 0.0, 0.0, 0.0};
            //This is the points Maverick can operate in
            public static double x1 = 0.0;
            public static double y1 = 0.0;
            public static double x2 = 0.0;
            public static double y2 = 0.0;
        }
        public static class Field{
                //The fields mesurements
                public static final double TARGET_HEIGHT = 1.2192; //Meters
                public static final double  CAMERA_PITCH = 0.3490659; //Radains
                //Field dimentions in meters
                public static final double FIELD_LENGHT = 16.46225;
                public static final double FIELD_WIDTH = 7.946136;
        }

        public static class AutoAim{
                
                public static final double CAMERA_HEIGHT = 0.381; //Meters
                public static final double LINEAR_P = 0;
                public static final double LINEAR_I = 0;
                public static final double LINEAR_D = 0;
                public static final double ANGULAR_P = 0;
                public static final double ANGULAR_I = 0;
                public static final double ANGULAR_D = 0;
        }

        public class Jetson{
                public static final int port = 5000;
                public static final String localhost = "127.0.0.1";
        }
}