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


import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc1388.frc2019official.RobotMap;
import org.usfirst.frc1388.frc2019official.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

import edu.wpi.first.wpilibj.Solenoid;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Climber extends Subsystem {

    Solenoid lifterLeft = new Solenoid( RobotMap.CANID_PCM_base, RobotMap.PCMCH_lifter_L );
    Solenoid lifterRight = new Solenoid( RobotMap.CANID_PCM_base, RobotMap.PCMCH_lifter_R );

    WPI_VictorSPX climbArm = new WPI_VictorSPX(RobotMap.CANID_climbArm);
    WPI_VictorSPX climbWheels = new WPI_VictorSPX(RobotMap.CANID_climbWheels);

    public Climber() {
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
        
        retractLifter();
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    public void extendLifter(){
        // activates airflow into the lifter actuators
        lifterLeft.set(true);
        lifterRight.set(true);
    }

    public void retractLifter(){
        // closes airflow from the lifter actuators
        lifterLeft.set(false);
        lifterRight.set(false);
    }

    public void runClimberArm( double speed ){
        climbArm.set( speed );
    }

    public void runClimberWheels( double speed ){
        climbWheels.set( speed );
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
}

