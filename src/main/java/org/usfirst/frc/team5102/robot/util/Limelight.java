package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight
{
    static Limelight limelightInstance;

    NetworkTable target;
	
	private Limelight(String address)
	{
		target = NetworkTableInstance.getDefault().getTable(address);
	}
	
	public double getTargetX()
	{
		return target.getEntry("tx").getDouble(0);
	}
	
	public double getTargetY()
	{
		return target.getEntry("ty").getDouble(0);
	}
	
	public double getArea()
	{
		return target.getEntry("ta").getDouble(0);
	}
	
	public boolean targetFound()
	{
        return (target.getEntry("tv").getDouble(0) == 1);
	}
	
	public void setLEDs(boolean state)
	{
        target.getEntry("ledMode").setDouble((state) ? 0 : 1);
    }
    
    public static Limelight getInstance()
	{
		if(limelightInstance == null)
			limelightInstance = new Limelight("limelight");
		return limelightInstance;
	}
}