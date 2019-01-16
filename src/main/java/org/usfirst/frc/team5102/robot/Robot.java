/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.subsystems.Drive;
import org.usfirst.frc.team5102.robot.subsystems.Grabber;
import org.usfirst.frc.team5102.robot.subsystems.SubsystemManager;
import org.usfirst.frc.team5102.robot.util.DriverStation5102;
import org.usfirst.frc.team5102.robot.util.DriverStation5102.RobotMode;

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
	@Override
	public void robotInit()
	{
		SubsystemManager.getInstance().addSubsystem(Drive.getInstance());
		SubsystemManager.getInstance().addSubsystem(Grabber.getInstance());
	}

	@Override
	public void autonomousInit()
	{
		DriverStation5102.getInstance().setMode(RobotMode.AUTON);

		SubsystemManager.getInstance().runAutonInit();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		SubsystemManager.getInstance().runAuton();
	}

	@Override
	public void teleopInit()
	{
		DriverStation5102.getInstance().setMode(RobotMode.TELEOP);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic()
	{
		SubsystemManager.getInstance().runTeleop();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic()
	{
		SubsystemManager.getInstance().runTest();
	}

	@Override
	public void disabledInit()
	{
		DriverStation5102.getInstance().setMode(RobotMode.DISABLED);
	}

	@Override
	public void disabledPeriodic()
	{
		
	}

	public void robotPeriodic()
	{
		DriverStation5102.getInstance().updateDS();
	}
}
