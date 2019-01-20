package org.usfirst.frc.team5102.robot.subsystems;

import org.usfirst.frc.team5102.robot.util.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.NidecBrushless;

public class Grabber extends Subsystem
{
    private static Grabber grabberInstance;

    private NidecBrushless grabberMotor;

    private int intakeSpeed = -1;
    private int shootSpeed = 1;

    private Grabber()
    {
        grabberMotor = new NidecBrushless(
            RobotMap.GRABBER_MOTOR_PWM,
            RobotMap.GRABBER_MOTOR_DIO);
    }

    public void intake()
    {
        grabberMotor.set(intakeSpeed);
    }
    public void shoot()
    {
        grabberMotor.set(shootSpeed);
    }
    public void stopMotors()
    {
        grabberMotor.set(0);
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
