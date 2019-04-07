package org.usfirst.frc.team5102.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.Toggle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class Lift extends Subsystem
{
    private static Lift liftInstance;

    private CANSparkMax liftMotor;
    private VictorSPX liftDriveMotor;

    private Solenoid climberSolenoid;

    private Toggle climberToggle;

    private boolean driving;

    public Lift()
    {
        liftMotor = new CANSparkMax(RobotMap.LIFT_MOTOR, MotorType.kBrushless);
        liftDriveMotor = new VictorSPX(RobotMap.LIFT_DRIVE_MOTOR);
        climberSolenoid = new Solenoid(RobotMap.CLIMBER_SOLENOID);

        climberToggle = new Toggle();
    }

    @Override
    public void autonInit()
    {
        climberSolenoid.set(false);
    }

    @Override
    public void teleopInit()
    {
        climberToggle.set(false);
    }

    @Override
    public void teleop()
    {
        climberSolenoid.set(
            climberToggle.toggle(ds.getDriveController().getYButton()));

        if(ds.getDriveController().getPOV() == 0)
        {
            driving = true;
            liftDriveMotor.set(ControlMode.PercentOutput, 0.50);

            //Drive.getInstance().getDrive().arcadeDrive(0.25, 0);
        }
        else if(ds.getDriveController().getPOV() == 180)
        {
            driving = true;
            liftDriveMotor.set(ControlMode.PercentOutput, -0.50);

            //Drive.getInstance().getDrive().arcadeDrive(-0.25, 0);
        }
        else
        {
            driving = false;
            liftDriveMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public boolean isDriving()
    {
        return driving;
    }

    public static Lift getInstance()
	{
		if(liftInstance == null)
			liftInstance = new Lift();
		return liftInstance;
	}
}
