/* (C)5687-2021 */
package org.frc5687.infiniterecharge.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.constraint.SwerveDriveKinematicsConstraint;
import org.frc5687.infiniterecharge.robot.Constants;
import org.frc5687.infiniterecharge.robot.OI;
import org.frc5687.infiniterecharge.robot.RobotMap;
import org.frc5687.infiniterecharge.robot.util.JetsonProxy;
import org.frc5687.infiniterecharge.robot.util.Limelight;
import org.frc5687.infiniterecharge.robot.util.OutliersContainer;

public class DriveTrain extends OutliersSubsystem {
    private final DiffSwerveModule _frontRight;
    private final DiffSwerveModule _frontLeft;
    private final DiffSwerveModule _backRight;
    private final DiffSwerveModule _backLeft;

    private final OutliersContainer _container;
    private final SwerveDriveKinematics _kinematics;
    private final SwerveDriveOdometry _odomerty;
    private final Limelight _limelight;
    private final JetsonProxy _proxy;

    private double _PIDAngle;
    private boolean _autoAim;

    private AHRS _imu;
    private OI _oi;

    private HolonomicDriveController _controller;
    private ProfiledPIDController _angleController;

    public DriveTrain(OutliersContainer container, Limelight limelight, JetsonProxy proxy, OI oi, AHRS imu) {
        super(container);
        _container = container;
        _limelight = limelight;
        _proxy = proxy;
        _oi = oi;
        _imu = imu;

        _frontRight =
                new DiffSwerveModule(
                        Constants.DriveTrain.FRONT_RIGHT_POSITION,
                        RobotMap.CAN.TALONFX.FR_LEFT_FALCON,
                        RobotMap.CAN.TALONFX.FR_RIGHT_FALCON,
                        RobotMap.DIO.ENCODER_FR,
                        Constants.DriveTrain.FRONT_RIGHT_ENCODER_OFFSET);
        _frontLeft =
                new DiffSwerveModule(
                        Constants.DriveTrain.FRONT_LEFT_POSITION,
                        RobotMap.CAN.TALONFX.FL_LEFT_FALCON,
                        RobotMap.CAN.TALONFX.FL_RIGHT_FALCON,
                        RobotMap.DIO.ENCODER_FL,
                        Constants.DriveTrain.FRONT_LEFT_ENCODER_OFFSET);
        _backRight =
                new DiffSwerveModule(
                        Constants.DriveTrain.BACK_RIGHT_POSITION,
                        RobotMap.CAN.TALONFX.BR_LEFT_FALCON,
                        RobotMap.CAN.TALONFX.BR_RIGHT_FALCON,
                        RobotMap.DIO.ENCODER_BR,
                        Constants.DriveTrain.BACK_RIGHT_ENCODER_OFFSET);
        _backLeft =
                new DiffSwerveModule(
                        Constants.DriveTrain.BACK_LEFT_POSITION,
                        RobotMap.CAN.TALONFX.BL_RIGHT_FALCON,
                        RobotMap.CAN.TALONFX.BL_LEFT_FALCON,
                        RobotMap.DIO.ENCODER_BL,
                        Constants.DriveTrain.BACK_LEFT_ENCODER_OFFSET);

        _kinematics =
                new SwerveDriveKinematics(
                        _frontLeft.getModulePosition(),
                        _frontRight.getModulePosition(),
                        _backLeft.getModulePosition(),
                        _backRight.getModulePosition());
        _odomerty = new SwerveDriveOdometry(_kinematics, getHeading());

        _controller =
                new HolonomicDriveController(
                        new PIDController(
                                Constants.DriveTrain.kP,
                                Constants.DriveTrain.kI,
                                Constants.DriveTrain.kD),
                        new PIDController(
                                Constants.DriveTrain.kP,
                                Constants.DriveTrain.kI,
                                Constants.DriveTrain.kD),
                        new ProfiledPIDController(
                                Constants.DriveTrain.kP,
                                Constants.DriveTrain.kI,
                                Constants.DriveTrain.kD,
                                new TrapezoidProfile.Constraints(
                                        Constants.DriveTrain.PROFILE_CONSTRAINT_VEL,
                                        Constants.DriveTrain.PROFILE_CONSTRAINT_ACCEL)));
        _angleController =
                new ProfiledPIDController(
                        Constants.DriveTrain.ANGLE_kP,
                        Constants.DriveTrain.ANGLE_kI,
                        Constants.DriveTrain.ANGLE_kD,
                        new TrapezoidProfile.Constraints(
                                Constants.DriveTrain.PROFILE_CONSTRAINT_VEL,
                                Constants.DriveTrain.PROFILE_CONSTRAINT_ACCEL));
        _angleController.enableContinuousInput(-Math.PI / 2.0, Math.PI / 2.0);
        _autoAim = false;
    }

    // use for modules as controller is running at 200Hz.
    public void controllerPeriodic() {
        _frontRight.periodic();
        _frontLeft.periodic();
        _backRight.periodic();
        _backLeft.periodic();
    }

    @Override
    public void periodic() {
        SwerveModuleState[] state = {
            _frontLeft.getState(),
            _frontRight.getState(),
            _backLeft.getState(),
            _backRight.getState()
        };
        if (!_proxy.isSocketNull()) {
            _proxy.sendOutFrame(new JetsonProxy.OutFrame(state));
        }

        //        _odomerty.update(
        //                getHeading(),
        //                _frontLeft.getState(),
        //                _frontRight.getState(),
        //                _backLeft.getState(),
        //                _backRight.getState());
    }

    @Override
    public void updateDashboard() {
        metric("BR/Encoder Angle", _backRight.getModuleAngle());
        metric("BL/Encoder Angle", _backLeft.getModuleAngle());
        metric("FL/Encoder Angle", _frontLeft.getModuleAngle());
        metric("FR/Encoder Angle", _frontRight.getModuleAngle());
        metric("Proxy/Pose", _proxy.getLatestFrame().getEstimatedPose().toString());

        //        metric("BR/Predicted Angle", _backRight.getPredictedAzimuthAngle());

        //        metric("BR/Encoder Azimuth Vel", _backRight.getAzimuthAngularVelocity());
        //        metric("BR/Predicted Azimuth Vel",
        // _backRight.getPredictedAzimuthAngularVelocity());

        //        metric("BR/Encoder Wheel Vel", _backRight.getWheelVelocity());
        //        metric("BR/Predicted Wheel Vel", _backRight.getPredictedWheelVelocity());

        //        metric("Odometry Pose", getOdometryPose().toString());
    }

    public void setFrontRightModuleState(SwerveModuleState state) {
        _frontRight.setIdealState(state);
    }

    public void setFrontLeftModuleState(SwerveModuleState state) {
        _frontLeft.setIdealState(state);
    }

    public void setBackLeftModuleState(SwerveModuleState state) {
        _backLeft.setIdealState(state);
    }

    public void setBackRightModuleState(SwerveModuleState state) {
        _backRight.setIdealState(state);
    }

    public double getYaw() {
        return _imu.getYaw();
    }

    // yaw is negative to follow wpi coordinate system.
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-getYaw());
    }

    public void resetYaw() {
        _imu.reset();
    }

    /**
     * Method to set correct module speeds and angle based on wanted vx, vy, omega
     *
     * @param vx velocity in x direction
     * @param vy velocity in y direction
     * @param omega angular velocity (rotating speed)
     * @param fieldRelative forward is always forward no mater orientation of robot.
     */
    public void drive(double vx, double vy, double omega, boolean fieldRelative) {
        if (Math.abs(vx) < Constants.DriveTrain.DEADBAND
                && Math.abs(vy) < Constants.DriveTrain.DEADBAND
                && Math.abs(omega) < Constants.DriveTrain.DEADBAND) {
            setFrontRightModuleState(
                    new SwerveModuleState(0, new Rotation2d(_frontRight.getModuleAngle())));
            setFrontLeftModuleState(
                    new SwerveModuleState(0, new Rotation2d(_frontLeft.getModuleAngle())));
            setBackRightModuleState(
                    new SwerveModuleState(0, new Rotation2d(_backRight.getModuleAngle())));
            setBackLeftModuleState(
                    new SwerveModuleState(0, new Rotation2d(_backLeft.getModuleAngle())));
            _PIDAngle = getHeading().getRadians();
            _angleController.reset(_PIDAngle);
        } else if (Math.abs(omega) > 0) {
            SwerveModuleState[] swerveModuleStates =
                    _kinematics.toSwerveModuleStates(
                            fieldRelative
                                    ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                            vx, vy, omega, getHeading())
                                    : new ChassisSpeeds(vx, vy, omega));
            SwerveDriveKinematics.normalizeWheelSpeeds(
                    swerveModuleStates, Constants.DriveTrain.MAX_MPS);
            setFrontRightModuleState(swerveModuleStates[1]);
            setFrontLeftModuleState(swerveModuleStates[0]);
            setBackLeftModuleState(swerveModuleStates[2]);
            setBackRightModuleState(swerveModuleStates[3]);
            _PIDAngle = getHeading().getRadians();
            _angleController.reset(_PIDAngle);
        } else {
            SwerveModuleState[] swerveModuleStates =
                    _kinematics.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    vx,
                                    vy,
                                    _angleController.calculate(
                                            getHeading().getRadians(), _PIDAngle),
                                    new Rotation2d(_PIDAngle)));
            SwerveDriveKinematics.normalizeWheelSpeeds(
                    swerveModuleStates, Constants.DriveTrain.MAX_MPS);
            setFrontRightModuleState(swerveModuleStates[1]);
            setFrontLeftModuleState(swerveModuleStates[0]);
            setBackLeftModuleState(swerveModuleStates[2]);
            setBackRightModuleState(swerveModuleStates[3]);
        }
    }

    public SwerveDriveKinematicsConstraint getKinematicConstraint() {
        return new SwerveDriveKinematicsConstraint(_kinematics, Constants.DriveTrain.MAX_MPS);
    }

    public TrajectoryConfig getConfig() {
        return new TrajectoryConfig(Constants.DriveTrain.MAX_MPS, Constants.DriveTrain.MAX_MPSS)
                .setKinematics(_kinematics)
                .addConstraint(getKinematicConstraint());
    }

    public void trajectoryFollower(Trajectory.State goal, Rotation2d heading) {
        ChassisSpeeds adjustedSpeeds =
                _controller.calculate(_odomerty.getPoseMeters(), goal, heading);
        SwerveModuleState[] moduleStates = _kinematics.toSwerveModuleStates(adjustedSpeeds);
        SwerveDriveKinematics.normalizeWheelSpeeds(moduleStates, Constants.DriveTrain.MAX_MPS);
        setFrontLeftModuleState(moduleStates[0]);
        setFrontRightModuleState(moduleStates[1]);
        setBackLeftModuleState(moduleStates[2]);
        setBackRightModuleState(moduleStates[3]);
    }

    public double getLimelightYaw() {
        if (_limelight.hasTarget()) {
            return _limelight.getTargetYaw();
        }
        return 0;
    }

    public boolean hasVisionTarget() {
        return _limelight.hasTarget();
    }

    public void setUseAutoAim(boolean autoAim) {
        _autoAim = autoAim;
    }

    public boolean autoAim() {
        return _autoAim;
    }

    public Pose2d getOdometryPose() {
        return _odomerty.getPoseMeters();
    }

    public void startModules() {
        _frontRight.start();
        _frontLeft.start();
        _backLeft.start();
        _backRight.start();
    }

    public double getDistanceToTarget() {
        return Math.sqrt(
                Math.pow(getOdometryPose().getX() - Constants.Field.TARGET_POSITION.getX(),2) +
                Math.pow(getOdometryPose().getY() - Constants.Field.TARGET_POSITION.getY(),2));
    }

    public double getAngleToTarget() {
        return Math.atan2(
                Constants.Field.TARGET_POSITION.getY() - getOdometryPose().getY(),
                Constants.Field.TARGET_POSITION.getX() - getOdometryPose().getX());
    }
}
