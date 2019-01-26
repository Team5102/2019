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

/**
 * Add your docs here.
 */
public class Arm extends PIDSubsystem
{
    private static Arm armInstance;

    private CANSparkMax armMotor;
    private SparkEncoder armPosition;

    public Arm()
    {
        super(0.05, 0.0001, 0.006);

        armMotor = new CANSparkMax(RobotMap.ARM_MOTOR, MotorType.kBrushless);
        armPosition = new SparkEncoder(armMotor);

        setOutputRange(-0.7, 0.7);
        setAbsoluteTolerance(0.5);
        setSetpoint(0);
    }

    @Override
    protected double returnPIDInput()
    {
        return armPosition.getPosition();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        armMotor.set(output);
    }

    @Override
    public void autonInit()
    {
        enable();
    }

    @Override
    public void auton()
    {
        setSetpoint(ds.getLeftSlider(0)*50);
    }

    @Override
    void teleopInit()
    {
        disable();
    }

    @Override
    void disabledInit()
    {
        disable();
    }

    public static Arm getInstance()
	{
		if(armInstance == null)
			armInstance = new Arm();
		return armInstance;
	}
}
