/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.SparkEncoder;

public class Elevator extends PIDSubsystem
{
    private static Elevator elevatorInstance;

    CANSparkMax elevatorMotor;
    SparkEncoder elevatorPosition;

    public Elevator()
    {
        super(1, 0, 0);

        elevatorMotor = new CANSparkMax(RobotMap.ELEVATOR_MOTOR, MotorType.kBrushless);
        elevatorPosition = new SparkEncoder(elevatorMotor);

        setOutputRange(-0.7, 0.7);
        setAbsoluteTolerance(0.5);
        setSetpoint(0);
    }

    @Override
    protected double returnPIDInput()
    {
        return elevatorPosition.getPosition();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        elevatorMotor.set(output);
    }

    public static Elevator getInstance()
	{
		if(elevatorInstance == null)
			elevatorInstance = new Elevator();
		return elevatorInstance;
	}
}
