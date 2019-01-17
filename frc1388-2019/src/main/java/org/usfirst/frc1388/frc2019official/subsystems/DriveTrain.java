// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1388.frc2019official.subsystems;


import org.usfirst.frc1388.frc2019official.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX leftFront;
    private WPI_TalonSRX rightFront;
    //private DifferentialDrive diffDrive;
    private WPI_TalonSRX  backLeft;
    private WPI_TalonSRX backRight;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public DifferentialDrive diffDrive;
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftFront = new WPI_TalonSRX(3);
        rightFront = new WPI_TalonSRX(2);
        backLeft = new WPI_TalonSRX(4);                    
        backRight = new WPI_TalonSRX(1);

        //useFrontWheelsOnly(); //2 motor controllers
        //useSpeedControllerGroups(); //2 speed controller groups
        useFollowMode(); //Follow mode, links sameside motors
        // diffDrive = new DifferentialDrive(leftFront, rightFront); // arcade drive

        addChild("DifferentialDrive",diffDrive);
        diffDrive.setSafetyEnabled(true);
        diffDrive.setExpiration(0.1);
        diffDrive.setMaxOutput(1.0);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void drive(double X, double Y)
    {
        diffDrive.arcadeDrive(X, Y);
    }
    
    public void useSpeedControllerGroups() {
        SpeedControllerGroup leftGroup = new SpeedControllerGroup( leftFront, backLeft );
        SpeedControllerGroup rightGroup = new SpeedControllerGroup( rightFront, backRight );

        diffDrive = new DifferentialDrive( leftGroup, rightGroup );
        
    }

    public void useFollowMode() {
        backLeft.follow( leftFront );
        backRight.follow( rightFront );

        diffDrive = new DifferentialDrive( leftFront, rightFront );
    }

    public void useFrontWheelsOnly() {
        diffDrive = new DifferentialDrive( leftFront, rightFront );
    }

}

