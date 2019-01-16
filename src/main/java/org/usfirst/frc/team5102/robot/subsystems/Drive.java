package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.util.DriverStation5102;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem
{
	private static Drive driveInstance;
	
	private CANSparkMax leftDriveMotor1, leftDriveMotor2, rightDriveMotor1, rightDriveMotor2;
	DifferentialDrive robotDrive;

	private Drive()
	{
		/*
		leftDriveMotor1 = new CANSparkMax(RobotMap.leftDriveMotor1, MotorType.kBrushless);
		leftDriveMotor2 = new CANSparkMax(RobotMap.leftDriveMotor2, MotorType.kBrushless);
		rightDriveMotor1 = new CANSparkMax(RobotMap.rightDriveMotor1, MotorType.kBrushless);
		rightDriveMotor2 = new CANSparkMax(RobotMap.rightDriveMotor2, MotorType.kBrushless);
		leftDriveMotor2.follow(leftDriveMotor1);
		rightDriveMotor2.follow(rightDriveMotor1);

		robotDrive = new DifferentialDrive(leftDriveMotor1, rightDriveMotor1);
		*/

		robotDrive = new DifferentialDrive(new Talon(0), new Talon(1));
	}

	public void teleop()
	{
		robotDrive.arcadeDrive(
			-DriverStation5102.getInstance().getDriveController().getY(Hand.kLeft)*DriverStation5102.getInstance().getRightSlider(1),
			DriverStation5102.getInstance().getDriveController().getX(Hand.kRight)*DriverStation5102.getInstance().getRightSlider(1)
		);
	}
	
	public static Drive getInstance()
	{
		if(driveInstance == null)
			driveInstance = new Drive();
		return driveInstance;
	}
}
