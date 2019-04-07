package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.subsystems.pid.DrivePID;
import org.usfirst.frc.team5102.robot.util.DigitBoard;
import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.Toggle;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem
{
	private static Drive driveInstance;
	
	private CANSparkMax leftDriveMotor1, leftDriveMotor2, leftDriveMotor3,
		rightDriveMotor1, rightDriveMotor2, rightDriveMotor3;
		
	private DifferentialDrive robotDrive;

	//private DrivePID pid;

	public enum DriveMode
	{
		NORMAL,
		DEFENSE
	}

	private DriveMode driveMode;

	private Drive()
	{
		leftDriveMotor1 = new CANSparkMax(RobotMap.LEFT_DRIVE_MOTOR_1, MotorType.kBrushless);
		leftDriveMotor2 = new CANSparkMax(RobotMap.LEFT_DRIVE_MOTOR_2, MotorType.kBrushless);
		leftDriveMotor3 = new CANSparkMax(RobotMap.LEFT_DRIVE_MOTOR_3, MotorType.kBrushless);
		rightDriveMotor1 = new CANSparkMax(RobotMap.RIGHT_DRIVE_MOTOR_1, MotorType.kBrushless);
		rightDriveMotor2 = new CANSparkMax(RobotMap.RIGHT_DRIVE_MOTOR_2, MotorType.kBrushless);
		rightDriveMotor3 = new CANSparkMax(RobotMap.RIGHT_DRIVE_MOTOR_3, MotorType.kBrushless);

		// leftDriveMotor1.restoreFactoryDefaults();
		// leftDriveMotor2.restoreFactoryDefaults();
		// leftDriveMotor3.restoreFactoryDefaults();
		// rightDriveMotor1.restoreFactoryDefaults();
		// rightDriveMotor2.restoreFactoryDefaults();
		// rightDriveMotor3.restoreFactoryDefaults();

		setIdleMode(IdleMode.kCoast);

		leftDriveMotor2.follow(leftDriveMotor1);
		leftDriveMotor3.follow(leftDriveMotor1);
		rightDriveMotor2.follow(rightDriveMotor1);
		rightDriveMotor3.follow(rightDriveMotor1);

		robotDrive = new DifferentialDrive(leftDriveMotor1, rightDriveMotor1);
		
		//robotDrive = new DifferentialDrive(new Talon(0), new Talon(1));

		//pid = DrivePID.getInstance();

		driveMode = DriveMode.NORMAL;
	}

	public void setIdleMode(IdleMode mode)
	{
		leftDriveMotor1.setIdleMode(mode);
		leftDriveMotor2.setIdleMode(mode);
		leftDriveMotor3.setIdleMode(mode);
		rightDriveMotor1.setIdleMode(mode);
		rightDriveMotor2.setIdleMode(mode);
		rightDriveMotor3.setIdleMode(mode);
	}

	public DifferentialDrive getDrive()
	{
		return robotDrive;
	}

	private double getInputMagnitude()
	{
		double magnitude = -ds.getDriveController().getY(Hand.kLeft);
		magnitude *= ds.getRightSlider(1);
		magnitude /= (1+(ds.getDriveController().getTriggerAxis(Hand.kLeft)));
		return magnitude;
	}
	private double getInputCurve()
	{
		double curve = ds.getDriveController().getX(Hand.kRight);
		curve *= ds.getRightSlider(1);
		curve /= (1+(ds.getDriveController().getTriggerAxis(Hand.kLeft)));
		return curve;
	}

	@Override
	public void teleopInit()
	{
		setIdleMode(IdleMode.kBrake);
	}

	@Override
	public void teleop()
	{
		if(!Lift.getInstance().isDriving())
		{
			if(driveMode.equals(DriveMode.DEFENSE))
				robotDrive.arcadeDrive(-getInputMagnitude(), getInputCurve());
			else
				robotDrive.arcadeDrive(getInputMagnitude(), getInputCurve());

			// robotDrive.arcadeDrive(
			// (-ds.getDriveController().getY(Hand.kLeft)*ds.getRightSlider(1))
			// /(1+(ds.getDriveController().getTriggerAxis(Hand.kLeft))),
			// (ds.getDriveController().getX(Hand.kRight)*ds.getRightSlider(1))
			// /(1+(ds.getDriveController().getTriggerAxis(Hand.kLeft))));
		}
	}

	@Override
	public void autonInit()
	{
		setIdleMode(IdleMode.kBrake);

		//pid.setDriveTarget(10);
		//pid.enable();
	}

	@Override
	public void auton()
	{
		robotDrive.arcadeDrive(0, 0);
		
		//pid.setDriveCurrentPos(driveEnc.getLeft());
		//System.out.println("pidGet: " + driveEnc.getLeft() + " - pidWrite: " + pid.getMagnitude());
	}

	@Override
	public void disabledInit()
	{
		setIdleMode(IdleMode.kCoast);
	}

	@Override
	public void disabled()
	{
		
	}

	@Override
	public void periodic()
	{
		if(ds.getDriveController().getStartButton())
			driveMode = DriveMode.NORMAL;
		else if(ds.getDriveController().getBackButton())
			driveMode = DriveMode.DEFENSE;

		if(driveMode.equals(DriveMode.NORMAL))
			SmartDashboard.putString("Drive Mode", "Normal");
		else if(driveMode.equals(DriveMode.DEFENSE))
			SmartDashboard.putString("Drive Mode", "Defense");
	}

	public static Drive getInstance()
	{
		if(driveInstance == null)
			driveInstance = new Drive();
		return driveInstance;
	}
}
