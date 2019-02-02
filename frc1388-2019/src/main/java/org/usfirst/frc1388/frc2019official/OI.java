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

import org.usfirst.frc1388.frc2019official.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Thread;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public enum Controller {
        DRIVER,
        OPER
    }

    XboxController driveController = new XboxController(1);
    XboxController opController = new XboxController(2);

    /*
     * time of single rumble in milliseconds
     */
    public long rumble_pulse_time = 100;
    public double rumble_strength = 1;

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Drive", new Drive());
        SmartDashboard.putData("ElevatorMove", new ElevatorMove());
        SmartDashboard.putData("BallGrab", new BallGrab());
        SmartDashboard.putData("PancakeMake", new PancakeMake());
        SmartDashboard.putData("Climb", new Climb());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    /**
     * Must be called from a context that does not loop continuously.
     * Use a flag to determine if a button has been released since the last check

     * @param num The number of equally spaced pulses
     *
     * FIX: This forces the calling thread to wait until pulses have completed
     */
    public void rumblePulse( Controller controller, int num ) {
        Timer timer = new Timer();

        TimerTask task = new TimerTask()
        {
            private void task_wait( long wait_time_ms )
            {
                // Start the clock
                long rumble_start_time = System.nanoTime();

                // Wait for wait_time_ms in nanoseconds
                while ( System.nanoTime() - rumble_start_time < wait_time_ms * 1000000 )
                { /* wait */ }
            }

            @Override
            public void run()
            {
                // Grab num from outer scope
                int n = num;

                while ( n > 0 )
                {
                    // Turn on the rumble
                    rumbleOn( controller );

                    // Wait for rumble_pulse_time
                    try {
                        Thread.sleep( rumble_pulse_time );
                    }
                    catch ( Exception e )
                    {
                        // Active wait
                        task_wait( rumble_pulse_time );
                    }

                    // Turn off the rumble
                    rumbleOff( controller );

                    // If this is the last pulse, do not pause for next pulse
                    if ( n <= 1 )
                        break;

                    // Wait for rumble_pulse_time
                    try {
                        Thread.sleep( rumble_pulse_time * 2 );
                    }
                    catch ( Exception e )
                    {
                        // Active wait
                        task_wait( rumble_pulse_time * 2 );
                    }

                    n--;
                }

                timer.cancel();
                timer.purge();
            }
        };

        // Start the pulses immediately
        timer.schedule( task, 0 );
    }

    public void rumbleOn( Controller controller ) {
        XboxController cont = driveController;

        switch ( controller )
        {
            case DRIVER:
                cont = driveController;
                break;
            case OPER:
                cont = opController;
                break;
        }

        cont.setRumble( RumbleType.kLeftRumble, rumble_strength );
        cont.setRumble( RumbleType.kRightRumble, rumble_strength );
    }

    public void rumbleOff( Controller controller ) {
        XboxController cont = driveController;

        switch ( controller )
        {
            case DRIVER:
                cont = driveController;
                break;
            case OPER:
                cont = opController;
                break;
        }

        cont.setRumble( RumbleType.kLeftRumble, 0 );
        cont.setRumble( RumbleType.kRightRumble, 0 );
    }

    public XboxController getDriveController() {
        return driveController;
    }

    public XboxController getOpController(){
        return opController;
    }
    
}

