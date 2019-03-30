package org.usfirst.frc.team5102.robot.subsystems;

import java.util.ArrayList;

public class SubsystemManager
{
	ArrayList<Subsystem> subsystems;
	
	private static SubsystemManager manager = new SubsystemManager();
	
	private SubsystemManager()
	{
		subsystems = new ArrayList<>();
	}
	
	public void addSubsystem(Subsystem system)
	{
		subsystems.add(system);
	}
	
	public void runTeleopInit()
	{
		for(Subsystem system : subsystems)
		{
			system.teleopInit();
		}
	}
	public void runTeleop()
	{
		for(Subsystem system : subsystems)
		{
			system.teleop();
		}
	}
	public void runAutonInit()
	{
		for(Subsystem system : subsystems)
		{
			system.autonInit();
		}
	}
	public void runAuton()
	{
		for(Subsystem system : subsystems)
		{
			system.auton();
		}
	}
	public void runTestInit()
	{
		for(Subsystem system : subsystems)
		{
			system.testInit();
		}
	}
	public void runTest()
	{
		for(Subsystem system : subsystems)
		{
			system.test();
		}
	}
	public void runDisabledInit()
	{
		for(Subsystem system : subsystems)
		{
			system.disabledInit();
		}
	}
	public void runDisabled()
	{
		for(Subsystem system : subsystems)
		{
			system.disabled();
		}
	}

	public void runPeriodic()
	{
		for(Subsystem system : subsystems)
		{
			system.periodic();
		}
	}

	public void disableAll()
	{
		for(Subsystem system : subsystems)
		{
			system.disable();
		}
	}
	public void enableAll()
	{
		for(Subsystem system : subsystems)
		{
			system.enable();
		}
	}
	
	public static SubsystemManager getInstance()
	{
		return manager;
	}
}
