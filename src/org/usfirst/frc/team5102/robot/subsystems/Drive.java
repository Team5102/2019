package org.usfirst.frc.team5102.robot.subsystems;

public class Drive extends Subsystem
{
	private static Drive driveInstance;
	
	private Drive()
	{
		
	}
	
	public static Drive getInstance()
	{
		if(driveInstance == null)
			driveInstance = new Drive();
		return driveInstance;
	}
}
