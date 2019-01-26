/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems.pid;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Add your docs here.
 */
public class DrivePID extends PIDInputOutput
{
    PIDController drivePID, turnPID;

    private static DrivePID drivePIDInstance;

    public DrivePID()
    {
        drivePID = new PIDController(1, 0, 0, driveIO, driveIO);  //initialize PID controller for forward/backward movement
        turnPID = new PIDController(1, 0, 0, turnIO, turnIO);   //initialize PID controller for rotational movement

        drivePID.setSetpoint(0);
        drivePID.setOutputRange(-0.6, 0.6);
        drivePID.setAbsoluteTolerance(1);
    }

    public double getMagnitude()    //returns the current PID output for straight movement
    {
        if(drivePID.isEnabled())
            return driveIO.drivePIDOutput;
        return 0;
    }

    public double getCurve()        //returns the current PID output for rotational movement
    {
        if(turnPID.isEnabled())
            return turnIO.turnPIDOutput;
        return 0;
    }

    public void setDriveTarget(double target)
    {
        drivePID.setSetpoint(target);
    }

    public void setTurnTarget(double target)
    {
        turnPID.setSetpoint(target);
    }

    public void enable()
    {
        drivePID.enable();
        turnPID.enable();

        System.out.println("pid enabled");
    }

    public void disable()
    {
        drivePID.disable();
        turnPID.disable();
    }

    public static DrivePID getInstance()
	{
		if(drivePIDInstance == null)
			drivePIDInstance = new DrivePID();
		return drivePIDInstance;
	}
}


