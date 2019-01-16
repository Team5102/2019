package org.usfirst.frc.team5102.robot.subsystems;

import org.usfirst.frc.team5102.robot.util.DriverStation5102;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.NidecBrushless;

public class Grabber extends Subsystem
{
    private static Grabber grabberInstance;

    private NidecBrushless grabberMotor1, grabberMotor2;

    private Grabber() {
        grabberMotor1 = new NidecBrushless(2, 0);
    }

    public void teleop()
    {
        if(DriverStation5102.getInstance()
        .getSecondaryController().getTriggerAxis(Hand.kLeft) > 0.5)
        {
            grabberMotor1.set(0.5);
        }
        else if(DriverStation5102.getInstance()
        .getSecondaryController().getTriggerAxis(Hand.kRight) > 0.5)
        {
            grabberMotor1.set(-0.5);
        }
        else
        {
            grabberMotor1.set(0);
        }
    }


    public static Grabber getInstance()
	{
		if(grabberInstance == null)
			grabberInstance = new Grabber();
		return grabberInstance;
	}
}
