/* (C)5687-2021 */
package org.frc5687.infiniterecharge.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.frc5687.infiniterecharge.robot.commands.Blinkins;
import org.frc5687.infiniterecharge.robot.util.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends OutliersRobot implements ILoggingSource {
    public static OutliersContainer.IdentityMode _identityMode =
            OutliersContainer.IdentityMode.competition;
    private RioLogger.LogLevel _dsLogLevel = RioLogger.LogLevel.warn;
    private RioLogger.LogLevel _fileLogLevel = RioLogger.LogLevel.warn;

    private int _updateTick = 0;
    private String _name;

    private RobotContainer _robotContainer;

    private Blinkins _blinkins;

    private boolean _fmsConnected;

    private Command _autoCommand;

    /**
     * This function is setRollerSpeed when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        try{
            loadConfigFromUSB();
        }
        catch(Exception e){
            error(e.getMessage());
        }
        RioLogger.getInstance().init(_fileLogLevel, _dsLogLevel);
        LiveWindow.disableAllTelemetry();
        DriverStation.getInstance().silenceJoystickConnectionWarning(true);

        metric("Identity", _identityMode.toString());
        info("Robot " + _name + " running in " + _identityMode.toString() + " mode");

        _robotContainer = new RobotContainer(this, _identityMode);
        _robotContainer.init();

        //initialize the blinkins on port 9 with a buffer size of 60
        _blinkins = new Blinkins(9, 60);

        // Periodically flushes metrics (might be good to configure enable/disable via USB config
        // file)
        new Notifier(MetricTracker::flushAll).startPeriodic(Constants.METRIC_FLUSH_PERIOD);

    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        ourPeriodic();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
     * uncomment the getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to the switch structure
     * below with additional strings. If using the SendableChooser make sure to add them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        _fmsConnected = DriverStation.getInstance().isFMSAttached();
        _autoCommand = _robotContainer.getAutonomousCommand();
        _robotContainer.autonomousInit();
        if (_autoCommand != null) {
            _autoCommand.schedule();
        }
    }

    public void teleopInit() {
        _fmsConnected = DriverStation.getInstance().isFMSAttached();
        _robotContainer.teleopInit();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    private void ourPeriodic() {
        //blinkin rainbow
        updateRainbow();
        _blinkins.updateData();

        // Example of starting a new row of metrics for all instrumented objects.
        // MetricTracker.newMetricRowAll();
        MetricTracker.newMetricRowAll();
        _robotContainer.periodic();
        CommandScheduler.getInstance().run();
        update();
        updateDashboard();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        RioLogger.getInstance().forceSync();
        RioLogger.getInstance().close();
        _robotContainer.disabledInit();
    }

    @Override
    public void disabledPeriodic() {
        super.disabledPeriodic();
        _robotContainer.disabledPeriodic();
    }

    public void updateDashboard() {
        _updateTick++;
        if (_updateTick >= Constants.TICKS_PER_UPDATE) {
            _updateTick = 0;
            _robotContainer.updateDashboard();
        }
    }

    private void loadConfigFromUSB() {
        String output_dir = "/U/"; // USB drive is mounted to /U on roboRIO
        try {
            String usbDir = "/U/"; // USB drive is mounted to /U on roboRIO
            String configFileName = usbDir + "frc5687.cfg";
            File configFile = new File(configFileName);
            FileReader reader = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processConfigLine(line);
            }

            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            _identityMode = OutliersContainer.IdentityMode.competition;
        }
    }

    private void processConfigLine(String line) {
        try {
            if (line.startsWith("#")) {
                return;
            }
            String[] a = line.split("=");
            if (a.length == 2) {
                String key = a[0].trim().toLowerCase();
                String value = a[1].trim();
                switch (key) {
                    case "name":
                        _name = value;
                        break;
                    case "mode":
                        _identityMode = OutliersContainer.IdentityMode.valueOf(value.toLowerCase());
                        break;
                    case "fileloglevel":
                        _fileLogLevel = RioLogger.LogLevel.valueOf(value.toLowerCase());
                        break;
                    case "dsloglevel":
                        _dsLogLevel = RioLogger.LogLevel.valueOf(value.toLowerCase());
                        break;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void update() {}

    private int rainbowFirstPixelHue = 0;

    private void updateRainbow() {
        // For every pixel
        for (var i = 0; i < _blinkins.bufferSize; i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final int hue = (rainbowFirstPixelHue + (i * 180 / _blinkins.bufferSize)) % 180;
            // Set the value
            _blinkins.blinkinBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowFirstPixelHue += 3;
        // Check bounds
        rainbowFirstPixelHue %= 180;
    }
}
