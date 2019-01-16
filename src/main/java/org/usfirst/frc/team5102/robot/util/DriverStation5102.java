package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverStation5102
{
	public Joystick launchpad1, launchpad2;
	private XboxController driveController, secondaryController;
	
	boolean dataMode = false;
	
	double airPressure;
	
	AbsoluteEncoder enc;

	int connectedCounter = 0;
	
	public enum RobotMode
	{
		AUTON,
		TELEOP,
		DISABLED
	}
	
	public enum InfoStripMode
	{
		AIRPRESSURE,
		INFO
	}
	
	boolean modeOverride;
	
	SendableChooser<String> chooser = new SendableChooser<>();
	SendableChooser<String> positionChooser = new SendableChooser<>();
		
	static DriverStation5102 ds;
	
	private DriverStation5102()
	{
		driveController = new XboxController(0);
		secondaryController = new XboxController(1);
		
		launchpad1 = new Joystick(2);
		launchpad2 = new Joystick(3);
		
		modeOverride = false;
		
		airPressure = 0;
		
		enc = new AbsoluteEncoder(launchpad1, 2);
		enc.setRange(0, 720);
		
		chooser.setDefaultOption("No Auton", "No Auton");
		chooser.addOption("Drive Forward", "Drive Forward");
		chooser.addOption("Drive To Switch", "Drive To Switch");
		chooser.addOption("Test Auton", "Test Auton");
		chooser.addOption("Capture Switch", "Capture Switch");
		SmartDashboard.putData("Auto Mode", chooser);
		positionChooser.addOption("Left", "Left");
		positionChooser.setDefaultOption("Center", "Center");
		positionChooser.addOption("Right", "Right");
		SmartDashboard.putData("Starting Position", positionChooser);
		
		setConnected();
	}
	
	public void updateDS()
	{
		setInfoStrip(0, airPressure, InfoStripMode.AIRPRESSURE);
		setInfoStrip(1, enc.getScaledPercent(15), InfoStripMode.INFO);
		
		if(driveController.getAButton())
		{
			enc.setValuePercent(75);
		}

		if(launchpad1.getRawAxis(3) == 0.0)
			connectedCounter++;
		else
			connectedCounter = 0;
	}

	public void setInfoStrip(int meter, double number, InfoStripMode mode)
	{
		if(mode == InfoStripMode.AIRPRESSURE)
		{
			int i, start;
			for(i = 10, start = 54; number > 0 && number > start; i--)
				start -= 6;
			number = i;
		}
			
		boolean[] pins = toBinary((int)number);

		setCommPins(meter, pins[0], pins[1], pins[2], pins[3]);
	}
	
	public void setMode(RobotMode mode)
	{
		switch(mode)
		{
			case DISABLED:
				launchpad1.setOutput(2, false);
				break;
			case AUTON:
			case TELEOP:
				launchpad1.setOutput(2, true);
				break;
		}
	}

	public boolean isConnected()
	{
		return !(connectedCounter > 10);
	}
	
	public Alliance getAlliance()
	{
		return DriverStation.getInstance().getAlliance();
	}
	
	public void updateAlliance()
	{
		boolean isAllianceRed = 
			getAlliance() == Alliance.Red;

		launchpad1.setOutput(3, isAllianceRed);
	}
	
	public void setAirPressure(double pressure)
	{
		airPressure = pressure;
	}
	
	public void setConnected()
	{
		launchpad1.setOutput(1, true);
	}
	
	public void setCommPins(int mode, boolean pin1, boolean pin2, boolean pin3, boolean pin4)
	{		
		if(mode == 0)
		{
			launchpad1.setOutput(7, pin1);
			launchpad1.setOutput(8, pin2);
			launchpad1.setOutput(9, pin3);
			launchpad1.setOutput(10, pin4);
		}
		else
		{
			launchpad2.setOutput(1, pin1);
			launchpad2.setOutput(2, pin2);
			launchpad2.setOutput(3, pin3);
			launchpad2.setOutput(4, pin4);
		}
	}
	
	public String getSelectedAuton()
	{
		return chooser.getSelected();
	}
	
	public boolean[] toBinary(int number)
	{
		boolean[] binary = new boolean[]{false,false,false,false};	//create new boolean array and set contents to zero
		
		if(number < 0 || number > 15)		//insure that input is within desirable range of 0-15
		{
			System.err.println("[DriverStation5102.toBinary()] Input is not in the range 0-15, returning zero...");
			
			return binary;
		}
		
		String binaryString = String.format("%04d", Integer.parseInt(Integer.toBinaryString(number)));	//convert number to binary
		
		for(int i = 0; i < 4; i++)		//set the boolean array to the binary values
		{
			if(binaryString.charAt(i) == '1')
			{
				binary[i] = true;
				//System.out.println("changing boolean value...");
			}
		}

		//System.out.println(number + " - " + binary[0] + " " + binary[1] + " " + binary[2] + " " + binary[3]);
		return binary;		//return the boolean array of binary values
	}

	public XboxController getDriveController()
	{
		return driveController;
	}
	public XboxController getSecondaryController()
	{
		return secondaryController;
	}

	public double getLeftSlider(double defaultValue)
	{
		if(isConnected())
			return (((launchpad1.getRawAxis(1)+1)/2));
		return defaultValue;
	}
	public double getRightSlider(double defaultValue)
	{
		if(isConnected())
			return (((launchpad1.getRawAxis(0)+1)/2));
		return defaultValue;
	}
	
	public static DriverStation5102 getInstance()
	{
		if(ds == null)
			ds = new DriverStation5102();
		return ds;
	}
}
