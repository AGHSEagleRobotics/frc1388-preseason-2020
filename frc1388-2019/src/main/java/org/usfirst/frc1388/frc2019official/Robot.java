// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1388.frc2019official;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import org.usfirst.frc1388.frc2019official.UsbLogging.Level;
import org.usfirst.frc1388.frc2019official.commands.*;
import org.usfirst.frc1388.frc2019official.subsystems.*;

import edu.wpi.first.cameraserver.CameraServer;;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    public static DriveTrain driveTrain;
    public static Climber climber;
    public static Elevator elevator;
    public static Manipulator manipulator;
    public static Air air;
    public static Vision vision;

    public static AnalogInput exampleAnalog = new AnalogInput(0);
    int raw;
    double volts;
    int averageRaw;  
    double averageVolts;

    public static DriverStation driverStation;
    ShuffleboardTab compDashboard = Shuffleboard.getTab("competition");

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Start up USB logging
        UsbLogging.openLog();
        UsbLogging.setLogLevel( Level.INFO );

        // print software version - use printLog so this always, always gets printed
        UsbLogging.printLog("Git version: " + BuildInfo.GIT_VERSION + " (branch: " + BuildInfo.GIT_BRANCH + BuildInfo.GIT_STATUS + ")");
        UsbLogging.printLog("Built: " + BuildInfo.BUILD_DATE + "  " + BuildInfo.BUILD_TIME);
 
        UsbLogging.info( "Robot.robotInit()" );

        /**
         * Initialize Subsystems
         */
        driveTrain = new DriveTrain();
        climber = new Climber();
        elevator = new Elevator();
        manipulator = new Manipulator();
        air = new Air();
        vision = new Vision();

        raw = exampleAnalog.getValue();
        volts = exampleAnalog.getVoltage();
        averageRaw = exampleAnalog.getAverageValue();
        averageVolts = exampleAnalog.getAverageVoltage();

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

        SmartDashboard.putData("Auto mode", chooser);

        Shuffleboard.getTab("competition")
        .add( "Camera Mode", 0)
        .withWidget( BuiltInWidgets.kComboBoxChooser)
        .getEntry();

        CameraServer.getInstance().startAutomaticCapture();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
        Robot.oi.rumbleOff( Robot.oi.driveController );
        Robot.oi.rumbleOff( Robot.oi.opController );

        UsbLogging.info("########  Robot disabled");
        
        //TODO:  might need to be removed or only run on a flag
        Robot.manipulator.initialize();

        // Turn off the camera's LED when the robot is disabled.
        Robot.vision.ledOff();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        UsbLogging.info("########  Autonomous enabled");
        
    	if (driverStation == null)
        driverStation = DriverStation.getInstance();

        // Get match info from FMS
    	if (driverStation.isFMSAttached()) {
    		String fmsInfo = "FMS info:";
    		fmsInfo += "  " + driverStation.getEventName();
    		fmsInfo += " " + driverStation.getMatchType();
    		fmsInfo += " match " + driverStation.getMatchNumber();
    		if (driverStation.getReplayNumber() > 0) {
    			fmsInfo += " replay " + driverStation.getReplayNumber();
    		}
    		fmsInfo += ";  " + driverStation.getAlliance() + " alliance";
    		fmsInfo += ",  Driver Station " + driverStation.getLocation();
    		UsbLogging.info(fmsInfo);
    	} else {
    		UsbLogging.info("FMS not connected");
        }
        
        // Turn on the camera's LED when the robot is enabled
        // TODO: Consider removing this
        Robot.vision.ledAuto();
        
        // if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        UsbLogging.info("########  Teleop enabled");
        
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();

        // Turn on the camera's LED when the robot is enabled
        // TODO: Consider removing this
        Robot.vision.ledAuto();
    }

    /**
     * This function is always called periodically
     */
    @Override
    public void robotPeriodic()
    {
        SmartDashboard.putNumber("Infrared Raw", raw );
        SmartDashboard.putNumber("Infrared volts", volts );
        SmartDashboard.putNumber("Infrared Average Raw", averageRaw );
        SmartDashboard.putNumber("Infrared Average Volts", averageVolts );
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
