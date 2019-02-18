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

import org.usfirst.frc.team5102.robot.util.RobotMap;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem
{
    private static Arm armInstance;

    private CANSparkMax armMotor;

    private CANPIDController armPID;

    public Arm()
    {
        armMotor = new CANSparkMax(RobotMap.ARM_MOTOR, MotorType.kBrushless);
        
        armPID = armMotor.getPIDController();
        armPID.setP(0.0);       //blank values, will tune later
        armPID.setI(0.0);
        armPID.setD(0.0);
        armPID.setSmartMotionMaxAccel(5, 0);
        armPID.setSmartMotionMaxVelocity(50, 0);
        armPID.setOutputRange(-1, 1);
        armPID.setSmartMotionAllowedClosedLoopError(1, 0);
        
        armMotor.getEncoder().setPosition(0);
    }

    public double getRawPosition()
    {
        return armMotor.getEncoder().getPosition();
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
    void disabledInit()
    {
        
    }

    public static Arm getInstance()
	{
		if(armInstance == null)
			armInstance = new Arm();
		return armInstance;
	}
}
