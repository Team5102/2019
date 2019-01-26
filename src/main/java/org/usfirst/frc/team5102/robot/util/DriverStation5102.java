package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverStation5102
{
	private static final int DRIVE_CONTROLLER_PORT = 0;
	private static final int SECONDARY_CONTROLLER_PORT = 1;
	private static final int LAUNCHPAD_1_PORT = 2;
	private static final int LAUNCHPAD_2_PORT = 3;
	private static final int ENCODER_AXIS = 2;			//launchpad 1
	private static final int LEFT_SLIDER_AXIS = 1;		//launchpad 1
	private static final int RIGHT_SLIDER_AXIS = 0;		//launchpad 1

	private static final int CONNECTED_PIN = 1;			//launchpad 1
	private static final int ENABLED_PIN = 2;			//launchpad 1
	private static final int ALLIANCE_PIN = 3;			//launchpad 1
	private static final int ENC_METER_COMM_PIN_1 = 1;	//launchpad 2
	private static final int ENC_METER_COMM_PIN_2 = 2;
	private static final int ENC_METER_COMM_PIN_3 = 3;
	private static final int ENC_METER_COMM_PIN_4 = 4;
	private static final int AIR_METER_COMM_PIN_1 = 7;	//launchpad 1
	private static final int AIR_METER_COMM_PIN_2 = 8;
	private static final int AIR_METER_COMM_PIN_3 = 9;
	private static final int AIR_METER_COMM_PIN_4 = 10;


	public Joystick launchpad1, launchpad2;
	private MyXbox driveController, secondaryController;
	
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
		driveController = new MyXbox(DRIVE_CONTROLLER_PORT);
		secondaryController = new MyXbox(SECONDARY_CONTROLLER_PORT);
		
		launchpad1 = new Joystick(LAUNCHPAD_1_PORT);
		launchpad2 = new Joystick(LAUNCHPAD_2_PORT);
		
		modeOverride = false;
		
		airPressure = 0;
		
		enc = new AbsoluteEncoder(launchpad1, ENCODER_AXIS);
		enc.setRange(0, 720);
		
		chooser.setDefaultOption("No Auton", "No Auton");
		chooser.addOption("Drive Forward", "Drive Forward");
		chooser.addOption("Test Auton", "Test Auton");
		SmartDashboard.putData("Auto Mode", chooser);

		positionChooser.addOption("Left", "Left");
		positionChooser.setDefaultOption("Center", "Center");
		positionChooser.addOption("Right", "Right");
		SmartDashboard.putData("Starting Position", positionChooser);
		
		setConnected();
	}
	
	public void updateDS()
	{
		setAirPressureStrip(airPressure);
		setEncoderStrip(enc.getScaledPercent(15));
		
		if(driveController.getAButton())
		{
			enc.setValuePercent(75);
		}

		if(launchpad1.getRawAxis(3) == 0.0)
			connectedCounter++;
		else
			connectedCounter = 0;
	}

	public void setEncoderStrip(double number)
	{
		boolean[] pins = toBinary((int)number);

		launchpad2.setOutput(ENC_METER_COMM_PIN_1, pins[0]);
		launchpad2.setOutput(ENC_METER_COMM_PIN_2, pins[1]);
		launchpad2.setOutput(ENC_METER_COMM_PIN_3, pins[2]);
		launchpad2.setOutput(ENC_METER_COMM_PIN_4, pins[3]);
	}

	private void setAirPressureStrip(double pressure)
	{
		double number = pressure;
		int convertedNumber = 0;
		while(number > 6 && convertedNumber <= 10)
		{
			number -= 6;
			convertedNumber++;
		}
		number = convertedNumber;

		boolean[] pins = toBinary((int)number);

		launchpad1.setOutput(AIR_METER_COMM_PIN_1, pins[0]);
		launchpad1.setOutput(AIR_METER_COMM_PIN_2, pins[1]);
		launchpad1.setOutput(AIR_METER_COMM_PIN_3, pins[2]);
		launchpad1.setOutput(AIR_METER_COMM_PIN_4, pins[3]);
	}
	
	public void setMode(RobotMode mode)
	{
		switch(mode)
		{
			case DISABLED:
				launchpad1.setOutput(ENABLED_PIN, false);
				break;
			case AUTON:
			case TELEOP:
				launchpad1.setOutput(ENABLED_PIN, true);
				break;
		}
	}

	public boolean isConnected()
	{
		return (connectedCounter < 10);
	}
	
	public Alliance getAlliance()
	{
		return DriverStation.getInstance().getAlliance();
	}
	
	public void updateAlliance()
	{
		boolean isAllianceRed = 
			getAlliance() == Alliance.Red;

		launchpad1.setOutput(ALLIANCE_PIN, isAllianceRed);
	}
	
	public void setAirPressure(double pressure)
	{
		airPressure = pressure;
	}
	
	public void setConnected()
	{
		launchpad1.setOutput(CONNECTED_PIN, true);
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
			}
		}
		return binary;		//return the boolean array of binary values
	}

	public MyXbox getDriveController()
	{
		return driveController;
	}
	public MyXbox getSecondaryController()
	{
		return secondaryController;
	}

	public double getLeftSlider(double defaultValue)
	{
		if(isConnected())
			return (((launchpad1.getRawAxis(LEFT_SLIDER_AXIS)+1)/2));
		return defaultValue;
	}
	public double getRightSlider(double defaultValue)
	{
		if(isConnected())
			return (((launchpad1.getRawAxis(RIGHT_SLIDER_AXIS)+1)/2));
		return defaultValue;
	}

	public double applyDeadband(double magnitude, double deadband)
	{
		if(Math.abs(magnitude) > deadband)
		{
			return magnitude;
		}
		return 0.00;
	}
	
	public static DriverStation5102 getInstance()
	{
		if(ds == null)
			ds = new DriverStation5102();
		return ds;
	}
}
