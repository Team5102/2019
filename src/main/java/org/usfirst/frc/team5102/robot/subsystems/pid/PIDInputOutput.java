/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.subsystems.pid;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Add your docs here.
 */
public class PIDInputOutput
{
    DrivePIDIO driveIO;
    TurnPIDIO turnIO;

    public PIDInputOutput()
    {
        driveIO = new DrivePIDIO();
        turnIO = new TurnPIDIO();
    }

    public void setDriveCurrentPos(double drivePIDInput)
    {
        driveIO.drivePIDInput = drivePIDInput;
    }

    public void setTurnCurrentPos(double turnPIDInput)
    {
        turnIO.turnPIDInput = turnPIDInput;
    }
}

class DrivePIDIO implements PIDOutput, PIDSource
{
    double drivePIDInput, drivePIDOutput;

    public DrivePIDIO()
    {
        drivePIDInput = 0;
        drivePIDOutput = 0;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet()
    {
        return drivePIDInput;
    }

    @Override
    public void pidWrite(double output)
    {
        drivePIDOutput = output;
    }

}

class TurnPIDIO implements PIDOutput, PIDSource
{
    double turnPIDInput, turnPIDOutput;

    public TurnPIDIO()
    {
        turnPIDInput = 0;
        turnPIDOutput = 0;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet()
    {
        return turnPIDInput;
    }

    @Override
    public void pidWrite(double output)
    {
        turnPIDOutput = output;
    }
}
