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

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import org.usfirst.frc1388.frc2019official.RobotMap;
import org.usfirst.frc1388.frc2019official.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 */
public class Climber extends Subsystem {

    DoubleSolenoid lifters;

    WPI_VictorSPX climbArm; 
    WPI_VictorSPX climbWheels;

    DigitalInput topArmLimit = new DigitalInput(RobotMap.DIO_climberArmTop);
    DigitalInput bottomArmLimit = new DigitalInput(RobotMap.DIO_climberArmBottom);

    public Climber() {

        lifters = new DoubleSolenoid( RobotMap.CANID_PCM_base, RobotMap.PCMCH_lifterPush, RobotMap.PCMCH_lifterPull );

        climbArm = new WPI_VictorSPX(RobotMap.CANID_climbArm);
        climbWheels = new WPI_VictorSPX(RobotMap.CANID_climbWheels);

        climbArm.setNeutralMode(NeutralMode.Brake);
        climbWheels.setNeutralMode(NeutralMode.Brake);
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
        lifters.set(DoubleSolenoid.Value.kForward);
    }

    public void retractLifter(){
        // closes airflow from the lifter actuators
        lifters.set(DoubleSolenoid.Value.kReverse);
    }

    public void runClimberArm( double speed ){
        climbArm.set( speed );
    }

    public void runClimberWheels( double speed ){
        climbWheels.set( speed );
    }

    public boolean armReachedTop() {
        boolean isActive = topArmLimit.get();
        return isActive;
    }

    public boolean armReachedBottom() {
        boolean isActive = bottomArmLimit.get();
        return isActive;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
}

