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
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc1388.frc2019official.Robot;
import org.usfirst.frc1388.frc2019official.UsbLogging;

/**
 *
 */
public class Drive extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private boolean precisionMode = false;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Drive() {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	UsbLogging.info(">>> " + this.getClass().getSimpleName() + " started");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        double driveLeftStickY = Robot.oi.getDriveController().getY(Hand.kLeft);
        double driveRightStickX = Robot.oi.getDriveController().getX(Hand.kRight);

        boolean leftStickButton = Robot.oi.getDriveController().getStickButtonPressed(Hand.kLeft);
        if (leftStickButton) {
            precisionMode = !precisionMode;

            if ( precisionMode )
                Robot.oi.rumblePulse( Robot.oi.driveController, RumbleType.kRightRumble, 2 );
            else
                Robot.oi.rumblePulse( Robot.oi.driveController, RumbleType.kLeftRumble, 2 );

        }

        if (precisionMode) {
            Robot.driveTrain.arcadeDrive(driveLeftStickY, -driveRightStickX);

            // For Tank Drive
            // double driveRightStickY = Robot.oi.getDriveController().getY(Hand.kRight);
            // Robot.driveTrain.tankDrive(driveLeftStickY, driveRightStickY);
        }
        else {
            Robot.driveTrain.cheesyDrive(driveLeftStickY, -driveRightStickX, precisionMode);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //turns off rumble when robot is disabled
        Robot.oi.rumbleOff( Robot.oi.driveController );
        Robot.oi.rumbleOff( Robot.oi.opController );
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
