// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1388.frc2019official.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc1388.frc2019official.Robot;
import org.usfirst.frc1388.frc2019official.UsbLogging;


/**
 *
 */
public class Climb extends Command {

    XboxController opCont = null;
    double deadband = 0.2;

    public Climb() {
        requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	UsbLogging.info(">>> " + this.getClass().getSimpleName() + " started");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        /**
         * Guard against constructing/destructing each frame
         */
        if ( opCont == null )
            opCont = Robot.oi.opController;

        boolean leftBumper = opCont.getBumperPressed(Hand.kLeft);
        boolean rightBumper = opCont.getBumperPressed(Hand.kRight);

        double rightStickY = opCont.getY(Hand.kRight);
        double leftStickY = opCont.getY(Hand.kLeft);

        if( leftBumper )
        {
            UsbLogging.info( "[Climb] Arm is Retracting" );
            Robot.climber.retractLifter();
        }
        else if( rightBumper )
        {
            UsbLogging.info( "[Climb] Arm is Extending" );
            Robot.climber.extendLifter();
        }

        /**
         * Apply deadband
         */
        rightStickY = Math.abs( rightStickY ) < deadband ? 0.0 : rightStickY;
        leftStickY = Math.abs( leftStickY ) < deadband ? 0.0 : leftStickY;

        /**
         * Pressing down on stick causes arm to climb
         */
        Robot.climber.runClimberArm( -rightStickY );

        /**
         * Pressing down on stick causes wheel to climb
         */
        Robot.climber.runClimberWheels( leftStickY );
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	UsbLogging.info("<<< " + this.getClass().getSimpleName() + " ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        UsbLogging.info("<<< " + this.getClass().getSimpleName() + " interrupted");
        end();
    }
}
