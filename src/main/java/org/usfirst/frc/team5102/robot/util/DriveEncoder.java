/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.util;

import com.revrobotics.CANSparkMax;

/**
 * Add your docs here.
 */
public class DriveEncoder
{
    private SparkEncoder leftEnc, rightEnc;

    public DriveEncoder(CANSparkMax leftMotor, CANSparkMax rightMotor)
    {
        leftEnc = new SparkEncoder(leftMotor);
        rightEnc = new SparkEncoder(rightMotor);
    }

    public void resetAll()
    {
        leftEnc.reset();
        rightEnc.reset();
    }

    public void resetLeft()
    {
        leftEnc.reset();
    }

    public void resetRight()
    {
        rightEnc.reset();
    }

    public double getLeft()
    {
        return leftEnc.getPosition();
    }

    public double getRight()
    {
        return -rightEnc.getPosition();
    }

    public double getAverage()
    {
        return (getRight()+getLeft())/2;
    }
}
