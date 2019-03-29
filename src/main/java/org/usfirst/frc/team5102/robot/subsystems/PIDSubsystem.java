package org.usfirst.frc.team5102.robot.subsystems;

import org.usfirst.frc.team5102.robot.subsystems.Subsystem;
import org.usfirst.frc.team5102.robot.util.PIDInterface;

public abstract class PIDSubsystem extends Subsystem implements PIDInterface
{
    protected double target = 0;

    public boolean onTarget(double position)
    {
        return (position > target-5 && position < target+5);
    }
}