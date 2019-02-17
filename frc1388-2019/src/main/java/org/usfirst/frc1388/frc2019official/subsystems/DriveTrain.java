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

import org.usfirst.frc1388.frc2019official.ADIS16448_IMU;
import org.usfirst.frc1388.frc2019official.Robot;
import org.usfirst.frc1388.frc2019official.RobotMap;
import org.usfirst.frc1388.frc2019official.commands.*;
import org.usfirst.frc1388.frc2019official.subsystems.DifferentialDrive1388;
import org.usfirst.frc1388.frc2019official.subsystems.talon.Gains;
import org.usfirst.frc1388.frc2019official.subsystems.talon.Constants;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.*;


/**
 *
 */
public class DriveTrain extends Subsystem {

    private WPI_TalonSRX leftFront;
    private WPI_TalonSRX rightFront;
    private WPI_VictorSPX leftBack;
    private WPI_VictorSPX rightBack;

    private DifferentialDrive1388 diffDrive;

    public DriveTrain() {

        ADIS16448_IMU gyro = new ADIS16448_IMU();

        leftFront = new WPI_TalonSRX(RobotMap.CANID_Drivetrain_LF);
        rightFront = new WPI_TalonSRX(RobotMap.CANID_Drivetrain_RF);
        leftBack = new WPI_VictorSPX(RobotMap.CANID_Drivetrain_LB);
        rightBack = new WPI_VictorSPX(RobotMap.CANID_Drivetrain_RB);

        useVelocityMode();
        setNeutralBrake();

        addChild("DifferentialDrive1388",diffDrive);
        diffDrive.setSafetyEnabled(true);
        diffDrive.setExpiration(0.1);
        diffDrive.setMaxOutput(1.0);
        diffDrive.setDeadband( 0.2 );
    }

    /*
     * The motors will apply the brake when
     *   joysticks are released
     */
    private void setNeutralBrake()
    {
        leftFront.setNeutralMode(NeutralMode.Brake);
		leftBack.setNeutralMode(NeutralMode.Brake);
		rightFront.setNeutralMode(NeutralMode.Brake);
		rightBack.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void arcadeDrive(double speed, double rotation) {
        diffDrive.arcadeDrive(speed, rotation);
    }

    public void cheesyDrive( double speed, double rotation, boolean isQuickTurn ){
        diffDrive.curvatureDrive( speed, rotation, isQuickTurn);
    }

    public void tankDrive( double leftSpeed, double rightSpeed ){
        diffDrive.tankDrive( leftSpeed, rightSpeed );
    }

    private void useVelocityMode() {
        for ( WPI_TalonSRX talon : new WPI_TalonSRX [] { leftFront, rightFront } )
        {
            /* Factory Default all hardware to prevent unexpected behaviour */
            talon.configFactoryDefault();

            /* Config sensor used for Primary PID [Velocity] */
            talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                    Constants.kPIDLoopIdx,
                    Constants.kTimeoutMs);

            /**
             * Phase sensor accordingly.
             * Positive Sensor Reading should match Green (blinking) Leds on Talon
             */
            talon.setSensorPhase(true);

            /* Config the peak and nominal outputs */
            talon.configNominalOutputForward(0, Constants.kTimeoutMs);
            talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
            talon.configPeakOutputForward(1, Constants.kTimeoutMs);
            talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

            /* Config the Velocity closed loop gains in slot0 */
            talon.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
            talon.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
            talon.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
            talon.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
        }

        /**
         * Use Follow Mode
         */
        leftBack.follow( leftFront );
        rightBack.follow( rightFront );

        diffDrive = new DifferentialDrive1388( leftFront, rightFront );
    }
}

