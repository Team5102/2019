/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import org.usfirst.frc.team5102.robot.Presets;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem
{
    private static Elevator elevatorInstance;

    private CANSparkMax elevatorMotor1, elevatorMotor2;

    private CANPIDController elevatorPID;

    private final int MAX_HEIGHT = 200;

    public Elevator()
    {
        elevatorMotor1 = new CANSparkMax(RobotMap.ELEVATOR_MOTOR_1, MotorType.kBrushless);
        elevatorMotor2 = new CANSparkMax(RobotMap.ELEVATOR_MOTOR_2, MotorType.kBrushless);
        elevatorMotor1.setIdleMode(IdleMode.kBrake);
        elevatorMotor2.setIdleMode(IdleMode.kBrake);

        elevatorMotor2.follow(elevatorMotor1, true);

        elevatorPID = elevatorMotor1.getPIDController();
        elevatorPID.setP(0.1);
        elevatorPID.setI(0.000001);
        elevatorPID.setD(0.0);
        elevatorPID.setSmartMotionMaxAccel(5, 0);
        elevatorPID.setSmartMotionMaxVelocity(50, 0);
        elevatorPID.setOutputRange(-1, 1);
        elevatorPID.setSmartMotionAllowedClosedLoopError(1, 0);
    }

    public void setPosition(int position)
    {
        super.target = position;
        elevatorPID.setReference(position, ControlType.kPosition);
    }

    public double getPosition()
    {
        return elevatorMotor1.getEncoder().getPosition();
    }

    public void stop()
    {
        elevatorMotor1.stopMotor();
    }

    public void disable()
    {
        elevatorMotor1.setIdleMode(IdleMode.kCoast);
        elevatorMotor2.setIdleMode(IdleMode.kCoast);
    }
    public void enable()
    {
        elevatorMotor1.setIdleMode(IdleMode.kBrake);
        elevatorMotor2.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void teleop()
    {
        if(!Presets.isRunning())
        {
            elevatorMotor1.set(-ds.getSecondaryController().getY(Hand.kLeft));
        }
    }

    @Override
    public void auton()
    {

    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Elevator Height", Math.round(getPosition()));

        if(elevatorMotor1.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
            elevatorMotor1.getEncoder().setPosition(0);
    }

    public static Elevator getInstance()
	{
		if(elevatorInstance == null)
			elevatorInstance = new Elevator();
		return elevatorInstance;
	}
}
