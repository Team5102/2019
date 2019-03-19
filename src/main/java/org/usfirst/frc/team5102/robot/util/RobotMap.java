package org.usfirst.frc.team5102.robot.util;

public interface RobotMap
{
	//====================MOTORS====================
	//Drive
	public static final int LEFT_DRIVE_MOTOR_1 = 1;		//CAN bus
	public static final int LEFT_DRIVE_MOTOR_2 = 2;
	public static final int LEFT_DRIVE_MOTOR_3 = 3;
	
	public static final int RIGHT_DRIVE_MOTOR_1 = 4;
	public static final int RIGHT_DRIVE_MOTOR_2 = 5;
	public static final int RIGHT_DRIVE_MOTOR_3 = 6;

	//Elevator
	public static final int ELEVATOR_MOTOR_1 = 7;		//CAN bus
	public static final int ELEVATOR_MOTOR_2 = 8;

	//Arm
	public static final int ARM_MOTOR = 9;			//CAN bus
	public static final int WRIST_MOTOR = 10;		//CAN bus

	//Grabber
	public static final int GRABBER_MOTOR = 11;		//CAN bus

	//Lift
	public static final int LIFT_MOTOR_1 = 13;		//CAN bus
	public static final int LIFT_MOTOR_2 = 14;

	
	//====================Pneumatics====================
	
	public static final int SHIFTER_SOLENOID = 0;
	
	//====================I/O====================
	//Pneumatics
	public static final int PRESSURE_SENSOR = 0;	//analog input

	public static final int BALL_LOADED = 9;		//DIO
	
}
