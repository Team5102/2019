/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class MyXbox extends XboxController
{
    private double defaultDeadband = 0.16;

    public MyXbox(int port)
    {
        super(port);
    }

    public MyXbox(int port, double defaultDeadband)
    {
        super(port);
        this.defaultDeadband = defaultDeadband;
    }

    @Override
    public double getY(Hand hand) 
    {
        return applyDeadband(super.getY(hand), defaultDeadband);
    }
    
    public double getY(Hand hand, double deadband)
    {
        return applyDeadband(super.getY(hand), deadband);
    }

    @Override
    public double getX(Hand hand) 
    {
        return applyDeadband(super.getX(hand), defaultDeadband);
    }
    
    public double getX(Hand hand, double deadband)
    {
        return applyDeadband(super.getX(hand), deadband);
    }

    
    private double applyDeadband(double magnitude, double deadband)
	{
        return (Math.abs(magnitude) > deadband) ? magnitude : 0.00;
    }
    
    public void setDefaultDeadband(double defaultDeadband)
    {
        this.defaultDeadband = defaultDeadband;
    }
}
