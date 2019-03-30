/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.subsystems.Arm;
import org.usfirst.frc.team5102.robot.subsystems.Drive;
import org.usfirst.frc.team5102.robot.subsystems.Elevator;
import org.usfirst.frc.team5102.robot.subsystems.Grabber;
import org.usfirst.frc.team5102.robot.subsystems.Lift;
import org.usfirst.frc.team5102.robot.subsystems.SubsystemManager;
import org.usfirst.frc.team5102.robot.subsystems.Wrist;
import org.usfirst.frc.team5102.robot.util.DigitBoard;
import org.usfirst.frc.team5102.robot.util.DriverStation5102;
import org.usfirst.frc.team5102.robot.util.DriverStation5102.RobotMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot
{
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	 SubsystemManager systems = SubsystemManager.getInstance();
	 DriverStation5102 ds = DriverStation5102.getInstance();

	@Override
	public void robotInit()
	{
		systems.addSubsystem(Drive.getInstance());
		systems.addSubsystem(Grabber.getInstance());
		systems.addSubsystem(Arm.getInstance());
		systems.addSubsystem(Wrist.getInstance());
		systems.addSubsystem(Elevator.getInstance());
		systems.addSubsystem(Lift.getInstance());

		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	@Override
	public void autonomousInit()
	{
		ds.setMode(RobotMode.AUTON);

		systems.enableAll();
		systems.runAutonInit();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		systems.runTeleop();
		PresetManager.teleop();
	}

	@Override
	public void teleopInit()
	{
		ds.setMode(RobotMode.TELEOP);

		systems.enableAll();
		systems.runTeleopInit();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic()
	{
		systems.runTeleop();
		PresetManager.teleop();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic()
	{
		systems.runTest();
	}

	@Override
	public void disabledInit()
	{
		ds.setMode(RobotMode.DISABLED);

		systems.runDisabledInit();
	}

	@Override
	public void disabledPeriodic()
	{
		systems.runDisabled();

		//System.out.println(Elevator.getInstance().getRawHeight());

		if(DigitBoard.getInstance().getA())
		{
			systems.disableAll();
		}
		else if(DigitBoard.getInstance().getB())
		{
			systems.enableAll();
		}
	}

	@Override
	public void robotPeriodic()
	{
		systems.runPeriodic();

		ds.updateDS();

		DigitBoard.getInstance().writeDigits(RobotController.getBatteryVoltage() + "");
	}
}
