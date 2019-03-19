package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.subsystems.pid.DrivePID;
import org.usfirst.frc.team5102.robot.util.DigitBoard;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem
{
	private static Drive driveInstance;
	
	private CANSparkMax leftDriveMotor1, leftDriveMotor2, leftDriveMotor3,
		rightDriveMotor1, rightDriveMotor2, rightDriveMotor3;
		
	private DifferentialDrive robotDrive;

	private DrivePID pid;

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

		leftDriveMotor1.setIdleMode(IdleMode.kBrake);
		leftDriveMotor2.setIdleMode(IdleMode.kBrake);
		leftDriveMotor3.setIdleMode(IdleMode.kBrake);
		rightDriveMotor1.setIdleMode(IdleMode.kBrake);
		rightDriveMotor2.setIdleMode(IdleMode.kBrake);
		rightDriveMotor3.setIdleMode(IdleMode.kBrake);

		leftDriveMotor2.follow(leftDriveMotor1);
		leftDriveMotor3.follow(leftDriveMotor1);
		rightDriveMotor2.follow(rightDriveMotor1);
		rightDriveMotor3.follow(rightDriveMotor1);

		robotDrive = new DifferentialDrive(leftDriveMotor1, rightDriveMotor1);
		
		//robotDrive = new DifferentialDrive(new Talon(0), new Talon(1));

		pid = DrivePID.getInstance();
	}

	public void teleop()
	{
		robotDrive.arcadeDrive(
			-ds.getDriveController().getY(Hand.kLeft)*ds.getRightSlider(1),
			ds.getDriveController().getX(Hand.kRight)*ds.getRightSlider(1)
		);
	}

	public void autonInit()
	{
		//pid.setDriveTarget(10);
		//pid.enable();
	}

	public void auton()
	{
		robotDrive.arcadeDrive(0, 0);
		
		//pid.setDriveCurrentPos(driveEnc.getLeft());
		//System.out.println("pidGet: " + driveEnc.getLeft() + " - pidWrite: " + pid.getMagnitude());
	}

	public void disabled()
	{
		
	}

	public static Drive getInstance()
	{
		if(driveInstance == null)
			driveInstance = new Drive();
		return driveInstance;
	}
}
