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

import org.usfirst.frc.team5102.robot.util.DigitBoard;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Wrist extends Subsystem
{
    private static Wrist wristInstance;

    private CANSparkMax wristMotor;
    private CANPIDController wristPID;

    public Wrist() {
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

    @Override
    public void teleop()
    {
        

        // DigitBoard.getInstance().writeDigits(wristMotor.getEncoder().getPosition() + "");
        System.out.println(wristMotor.getEncoder().getPosition());

        if(wristMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
            wristMotor.getEncoder().setPosition(0);
        else if(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
            wristMotor.getEncoder().setPosition(47);

        if(ds.getSecondaryController().getAButton())
        {
            wristPID.setReference(30, ControlType.kPosition);
        }
        else if(ds.getSecondaryController().getBButton())
        {
            wristPID.setReference(-3, ControlType.kPosition);
        }
        else if(ds.getSecondaryController().getXButton())
        {
            wristPID.setReference(42, ControlType.kPosition);
        }
        else
        {
            wristMotor.set(ds.getSecondaryController().getY(Hand.kRight));
        }
    }

    public void disabled()
    {
        //DigitBoard.getInstance().writeDigits(wristMotor.getEncoder().getPosition()*10 + "");
        System.out.println(wristMotor.getEncoder().getPosition());
        //System.out.println(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen));

        if(wristMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get())
            wristMotor.getEncoder().setPosition(0);
        DigitBoard.getInstance().writeDigits(wristMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get() ? "true" : "false");
    }

    public static Wrist getInstance()
	{
		if(wristInstance == null)
			wristInstance = new Wrist();
		return wristInstance;
	}
}
