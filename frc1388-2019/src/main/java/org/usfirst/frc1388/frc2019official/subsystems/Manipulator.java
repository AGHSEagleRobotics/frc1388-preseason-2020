/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc1388.frc2019official.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc1388.frc2019official.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc1388.frc2019official.RobotMap;

/**
 * Add your docs here.
 */
public class Manipulator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /*
  * TODO: double check the pneumatic controller module IDs and channel IDs
  *
  */
  // DoubleSolenoid manipulator = new DoubleSolenoid(RobotMap.ID_PCM12v_channel2, RobotMap.ID_PCM12v_channel3); // ball grabber
  DoubleSolenoid manipulator = new DoubleSolenoid(RobotMap.CANID_PCM12v, RobotMap.PCMCH_manipulatorPush, RobotMap.PCMCH_manipulatorPull); // ball grabber
  Solenoid ejector = new Solenoid(RobotMap.CANID_PCM12v, RobotMap.PCMCH_ejector); // ball ejector

  DoubleSolenoid pancakeMaker = new DoubleSolenoid(RobotMap.CANID_PCM12v, RobotMap.PCMCH_pancakeMakerPush, RobotMap.PCMCH_pancakeMakerPull); // pancake maker
  Solenoid pancakeEjector = new Solenoid(RobotMap.CANID_PCM12v, RobotMap.PCMCH_pancakeEjector); // pancake eject

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    setDefaultCommand(new Manipulate());

    manipulator.set(DoubleSolenoid.Value.kOff);
    ejector.set(false);

    pancakeMaker.set(DoubleSolenoid.Value.kOff);
    pancakeEjector.set(false);

  }

  public void initialize() {
    manipulator.set(DoubleSolenoid.Value.kOff);
    ejector.set(false);

    pancakeMaker.set(DoubleSolenoid.Value.kOff);
    pancakeEjector.set(false);
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop

  }

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void ballGrab() {

    // Claw closes when actuator is retracted
    manipulator.set(DoubleSolenoid.Value.kReverse);

  }

  public void ballRelease() {

    // Claw opens when actuator is extended
    manipulator.set(DoubleSolenoid.Value.kForward);

  }

  public void ballEjectorExtend() {

    // Ball is ejected when actuator is extended
    ejector.set(true);

  }

  public void ballEjectorRetract() {

    // retract ball ejector
    ejector.set(false);

  }

  public void pancakeUp() {

    pancakeMaker.set(DoubleSolenoid.Value.kReverse);

  }

  public void pancakeDown() {

    pancakeMaker.set(DoubleSolenoid.Value.kForward);

  }

  public void pancakeEject() {

    pancakeEjector.set(true);

  }

  public void pancakeRetract() {

    pancakeEjector.set(false);

  }

}
