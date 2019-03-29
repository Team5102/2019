package org.usfirst.frc.team5102.robot.util;

public interface PIDInterface
{
    public void setPosition(int position);
    public double getPosition();
    public boolean onTarget(double position);
}
