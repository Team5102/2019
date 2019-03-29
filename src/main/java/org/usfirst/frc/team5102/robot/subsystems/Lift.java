package org.usfirst.frc.team5102.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.util.RobotMap;

public class Lift extends Subsystem
{
    private static Lift liftInstance;

    private CANSparkMax liftMotor;
    private VictorSPX liftDriveMotor;

    public Lift()
    {
        liftMotor = new CANSparkMax(RobotMap.LIFT_MOTOR, MotorType.kBrushless);
        liftDriveMotor = new VictorSPX(RobotMap.LIFT_DRIVE_MOTOR);
    }

    public static Lift getInstance()
	{
		if(liftInstance == null)
			liftInstance = new Lift();
		return liftInstance;
	}
}
