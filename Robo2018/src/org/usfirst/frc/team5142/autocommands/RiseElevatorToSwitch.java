package org.usfirst.frc.team5142.autocommands;

import org.usfirst.frc.team5142.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RiseElevatorToSwitch extends Command {

    public RiseElevatorToSwitch() {
      
    	
    	requires(Robot.elevator);
    	
    	setTimeout(0.2);
    	
    	
    	
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    Robot.elevator.TurnOnLift();
    
    
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
