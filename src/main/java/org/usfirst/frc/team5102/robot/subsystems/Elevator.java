/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import org.usfirst.frc.team5102.robot.util.DigitBoard;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Elevator extends Subsystem
{
    private static Elevator elevatorInstance;

    private CANSparkMax elevatorMotor1, elevatorMotor2;

    private CANPIDController elevatorPID;

    private final int MAX_HEIGHT = 100; //initial value, will change later

    public Elevator()
    {
        elevatorMotor1 = new CANSparkMax(RobotMap.ELEVATOR_MOTOR_1, MotorType.kBrushless);
        elevatorMotor2 = new CANSparkMax(RobotMap.ELEVATOR_MOTOR_2, MotorType.kBrushless);
        elevatorMotor1.setIdleMode(IdleMode.kBrake);
        elevatorMotor2.setIdleMode(IdleMode.kBrake);

        elevatorMotor2.follow(elevatorMotor1, true);

        elevatorPID = elevatorMotor1.getPIDController();
        elevatorPID.setP(0.04);
        elevatorPID.setI(0.00001);
        elevatorPID.setD(0.0);
        elevatorPID.setSmartMotionMaxAccel(5, 0);
        elevatorPID.setSmartMotionMaxVelocity(50, 0);
        elevatorPID.setOutputRange(-1, 1);
        elevatorPID.setSmartMotionAllowedClosedLoopError(1, 0);
    }

    public double getRawHeight()
    {
        return elevatorMotor1.getEncoder().getPosition();
    }

    @Override
    public void teleop()
    {
        elevatorMotor1.set(ds.getSecondaryController().getY(Hand.kLeft));
    }

    @Override
    public void auton()
    {
        System.out.println(getRawHeight() + " - " + elevatorMotor1.get());
        elevatorPID.setReference(10, ControlType.kSmartMotion);

        //DigitBoard.getInstance().writeDigits(ds.getEncoder().getPercent() + "");

        // if(DigitBoard.getInstance().getA())
        // {
        //     ds.getEncoder().setValueDegrees(180);
        // }
        // else if(DigitBoard.getInstance().getB())
        // {
        //     ds.getEncoder().setValueDegrees(360);
        // }
    }

    public static Elevator getInstance()
	{
		if(elevatorInstance == null)
			elevatorInstance = new Elevator();
		return elevatorInstance;
	}
}
