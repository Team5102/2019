package org.usfirst.frc.team5102.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Grabber extends Subsystem
{
    private static Grabber grabberInstance;

    private VictorSPX grabberMotor;

    private double intakeSpeed = -1;
    private double shootSpeed = 1;

    private Grabber()
    {
        grabberMotor = new VictorSPX(RobotMap.GRABBER_MOTOR);
    }

    public void intake()
    {
        grabberMotor.set(ControlMode.PercentOutput, intakeSpeed);
    }
    public void shoot()
    {
        grabberMotor.set(ControlMode.PercentOutput, shootSpeed);
    }
    public void stopMotors()
    {
        grabberMotor.set(ControlMode.PercentOutput, 0);
    }

    public void teleop()
    {
        if(ds.getSecondaryController().getTriggerAxis(Hand.kLeft) > 0.5)
        {
            intake();
        }
        else if(ds.getSecondaryController().getTriggerAxis(Hand.kRight) > 0.5)
        {
            shoot();
        }
        else
        {
            stopMotors();
        }
    }


    public static Grabber getInstance()
	{
		if(grabberInstance == null)
			grabberInstance = new Grabber();
		return grabberInstance;
	}
}
