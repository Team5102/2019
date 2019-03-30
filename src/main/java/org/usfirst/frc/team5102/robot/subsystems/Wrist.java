/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import org.usfirst.frc.team5102.robot.Presets;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends PIDSubsystem
{
    private static Wrist wristInstance;

    private CANSparkMax wristMotor;
    private CANPIDController wristPID;

    private boolean switchTriggered = false;

    public Wrist()
    {
        wristMotor = new CANSparkMax(RobotMap.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.restoreFactoryDefaults();
        wristMotor = new CANSparkMax(RobotMap.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.setInverted(false);
        wristMotor.setIdleMode(IdleMode.kBrake);

        wristPID = wristMotor.getPIDController();
        wristPID.setP(0.1);
        wristPID.setI(0.00000);
        wristPID.setD(0.0);
        wristPID.setSmartMotionMaxAccel(5, 0);
        wristPID.setSmartMotionMaxVelocity(30, 0);
        wristPID.setOutputRange(-0.8, 0.8);
        wristPID.setSmartMotionAllowedClosedLoopError(5, 0);
    }

    public double getPosition()
    {
        return wristMotor.getEncoder().getPosition();
    }

    public void setPosition(int position)
    {
        super.target = position;
        wristPID.setReference(position, ControlType.kPosition);
    }

    public void stop()
    {
        wristMotor.stopMotor();
    }

    public void disable()
    {
        wristMotor.setIdleMode(IdleMode.kCoast);
    }
    public void enable()
    {
        wristMotor.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void teleopInit()
    {
        switchTriggered = false;
        setPosition(0);
    }

    @Override
    public void teleop()
    {
        if(!Presets.isRunning())
        {
            if(ds.getSecondaryController().getAButton())
            {
                setPosition(25);
            }
            else if(ds.getSecondaryController().getBButton())
            {
                setPosition(0);
            }
            else if(ds.getSecondaryController().getXButton())
            {
                setPosition(50);
            }
        }
    }

    public void disabled()
    {
        //DigitBoard.getInstance().writeDigits(wristMotor.getEncoder().getPosition()*10 + "");
        //System.out.println(wristMotor.getEncoder().getPosition());
        //System.out.println(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen));

        if(wristMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
            wristMotor.getEncoder().setPosition(0);
        //DigitBoard.getInstance().writeDigits(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get() ? "true" : "false");
    }

    @Override
    public void periodic()
    {

        SmartDashboard.putNumber("Wrist Position", Math.round(getPosition()));

        if(wristMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
        {
            if(!switchTriggered)
            {
                wristMotor.getEncoder().setPosition(-2);
                switchTriggered = true;
            }
            //wristMotor.getEncoder().setPosition(-2);
        }
        else if(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
        {
            if(!switchTriggered)
            {
                wristMotor.getEncoder().setPosition(47);
                switchTriggered = true;
            }
        }
        else
        {
            switchTriggered = false;
        }
    }

    public static Wrist getInstance()
	{
		if(wristInstance == null)
			wristInstance = new Wrist();
		return wristInstance;
	}
}
