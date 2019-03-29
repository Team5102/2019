/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.Presets;
import org.usfirst.frc.team5102.robot.Presets.Preset;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends PIDSubsystem
{
    private static Arm armInstance;

    private CANSparkMax armMotor;
    private CANPIDController armPID;

    boolean switchTriggered = false;

    private final double HOLD_PERCENT = 0.02;

    public Arm()
    {
        armMotor = new CANSparkMax(RobotMap.ARM_MOTOR, MotorType.kBrushless);
        armMotor.setIdleMode(IdleMode.kBrake);
        armMotor.setInverted(true);
        
        armPID = armMotor.getPIDController();
        armPID.setP(0.05);       //blank values, will tune later
        armPID.setI(0.00001);
        armPID.setD(0.00);
        armPID.setSmartMotionMaxAccel(5, 0);
        armPID.setSmartMotionMaxVelocity(50, 0);
        armPID.setOutputRange(-0.75, 0.75);
        armPID.setSmartMotionAllowedClosedLoopError(1, 0);
        
        armMotor.getEncoder().setPosition(0);
    }

    public double getPosition()
    {
        return armMotor.getEncoder().getPosition();
    }

    public void setPosition(int position)
    {
        super.target = position;
        armPID.setReference(position, ControlType.kPosition);
    }

    public void stop()
    {
        armMotor.stopMotor();
    }

    public void disable()
    {
        armMotor.setIdleMode(IdleMode.kCoast);
    }
    public void enable()
    {
        armMotor.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void autonInit()
    {
        
    }

    @Override
    public void auton()
    {
        
    }

    @Override
    void teleopInit()
    {
        
    }

    @Override
    public void teleop()
    {
        if(!Presets.isRunning())
        {
            armMotor.set(-ds.getSecondaryController().getY(Hand.kRight)+HOLD_PERCENT);  
        }
    }

    @Override
    void disabledInit()
    {
        
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Arm Position", Math.round(getPosition()));

        if(armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
        {
            if(!switchTriggered)
            {
                armMotor.getEncoder().setPosition(0);
                switchTriggered = true;
            }
        }
        else
        {
            switchTriggered = false;
        }
    }

    public static Arm getInstance()
	{
		if(armInstance == null)
			armInstance = new Arm();
		return armInstance;
	}
}
