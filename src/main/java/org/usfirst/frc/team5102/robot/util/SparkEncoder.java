/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.util;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

/**
 * Add your docs here.
 */
public class SparkEncoder extends CANEncoder
{
    private double offset;

    public SparkEncoder(CANSparkMax spark)
    {
        super(spark);
        reset();
    }

    public void reset()
    {
        offset = super.getPosition();
    }

    @Override
    public double getPosition()
    {
        return super.getPosition()-offset;
    }
}
