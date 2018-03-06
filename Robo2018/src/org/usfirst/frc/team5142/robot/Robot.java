/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5142.robot;

import org.usfirst.frc.team5142.autopaths.DriveAndTurn;
import org.usfirst.frc.team5142.autocommands.DriveWithEncoders;
//import org.usfirst.frc.team5142.autocommands.DriveAround;
//import org.usfirst.frc.team5142.robot.subsystems.AutoMXP;
import org.usfirst.frc.team5142.robot.subsystems.Drivetrain;
import org.usfirst.frc.team5142.robot.subsystems.Elevator;
import org.usfirst.frc.team5142.robot.subsystems.Grabber;
import org.usfirst.frc.team5142.robot.subsystems.Limelight;
import org.usfirst.frc.team5142.robot.subsystems.NotGyro;
import org.usfirst.frc.team5142.robot.subsystems.Pusher;
import org.usfirst.frc.team5142.robot.util.GameState;

//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	//public static final ExampleSubsystem kExampleSubsystem
	//		= new ExampleSubsystem();
	public static OI oi;
	public static final Drivetrain drivetrain = new Drivetrain (); 
	public static final NotGyro gyro = new NotGyro();
	public static Grabber grabber = new Grabber();
	//public static AutoMXP NotAutoMXP = new AutoMXP();
	public static Pusher pusher = new Pusher();
	public static Elevator elevator = new Elevator();
	public static Limelight limelight = new Limelight();
	public GameState gameState;
	
	
//	final String defaultAuto = "Default";
//	final String customAuto = "My Auto";
//	Command autoSelected;
//	SendableChooser<String> chooser = new SendableChooser<>();

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		m_chooser.addDefault("Cross BaseLine", new DriveWithEncoders(121,0.75,2,0.02));
		m_chooser.addObject("My Auto", new DriveAndTurn());
		SmartDashboard.putData("Auto mode", m_chooser);
		CameraServer.getInstance().startAutomaticCapture(); //camera start
		
		Robot.elevator.TurnOffNotLift();
	
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {


		Robot.elevator.TurnOffNotLift();
	
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	
		this.gameState = new GameState(DriverStation.getInstance().getGameSpecificMessage());
	
	
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		Robot.elevator.TurnOffNotLift();
	//	if(Robot.NotAutoMXP.GetNumber1()){
		//	autoSelected = new DriveAround();
	//	}
		
	//	if(Robot.NotAutoMXP.GetNumber2()){
			
		//}
	//	Robot.drivetrain.ResetEncoder();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	

		
		//autoSelected.start();
		SmartDashboard.putNumber("RightEncoder",drivetrain.GetRightEncoder() );
		SmartDashboard.putNumber("LeftEncoder",drivetrain.GetLeftEncoder());
		//SmartDashboard.putNumber("gyro angle",gyro.GetAngle() );
	
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		Robot.drivetrain.front.setEnabled(true);
		Robot.elevator.TurnOffNotLift();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putNumber("Front UltraSonic", drivetrain.ReadUltrasonic());
		SmartDashboard.putNumber("gyro angle",gyro.GetYaw() );
//		SmartDashboard.putBoolean("Bit0",NotAutoMXP.GetNumber1() );
//		SmartDashboard.putBoolean("Bit1",NotAutoMXP.GetNumber2() );
//		SmartDashboard.putBoolean("Bit2",NotAutoMXP.GetNumber3() );
//		SmartDashboard.putBoolean("Bit3",NotAutoMXP.GetNumber4() );
		SmartDashboard.putNumber("RightEncoder",drivetrain.GetRightEncoder() );
		SmartDashboard.putNumber("LeftEncoder",drivetrain.GetLeftEncoder());
		SmartDashboard.putNumber("NotFront UltraSonic", drivetrain.ReadUltrasonic2());
		SmartDashboard.putNumber("LimelightXValue", limelight.GetX());
		SmartDashboard.putNumber("LimelightYValue", limelight.GetY());
		SmartDashboard.putNumber("LimelightAreaValue", limelight.GetArea());
	
	
	
	
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
