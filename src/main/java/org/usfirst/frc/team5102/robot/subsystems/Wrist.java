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

public class Wrist extends PIDSubsystem
{
    private static Wrist wristInstance;

    private CANSparkMax wristMotor;

    public Wrist()
    {
        super(1, 0, 0);

        wristMotor = new CANSparkMax(RobotMap.WRIST_MOTOR, MotorType.kBrushless);
    }

    @Override
    public void teleop()
    {

    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output)
    {
        wristMotor.set(output);
    }

    public static Wrist getInstance()
	{
		if(wristInstance == null)
			wristInstance = new Wrist();
		return wristInstance;
	}
}
