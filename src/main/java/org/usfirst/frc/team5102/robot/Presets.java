package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.subsystems.Arm;
import org.usfirst.frc.team5102.robot.subsystems.Elevator;
import org.usfirst.frc.team5102.robot.subsystems.Wrist;
import org.usfirst.frc.team5102.robot.util.PIDInterface;

public class Presets extends Thread
{
    Arm arm;
    Wrist wrist;
    Elevator elevator;

    public enum Preset
    {
        HatchPickup,
        LowHatch,
        MidHatch,
        HighHatch,
        LowBall,
        MidBall,
        HighBall
    }

    Preset preset;
    private static boolean isRunning = false;

    public Presets(Preset preset)
    {
        this.preset = preset;

        arm = Arm.getInstance();
        wrist = Wrist.getInstance();
        elevator = Elevator.getInstance();
    }

    @Override
    public void run()
    {

        isRunning = true;
        switch(preset)
        {
            case HatchPickup:
                hatchPickup();
                break;
            case LowHatch:
                lowHatch();
                break;
            case MidHatch:
                midHatch();
                break;
            // case HighHatch:
            //     highHatch();
            //     break;
            case LowBall:
                lowBall();
                break;
            case MidBall:
                midBall();
                break;
            // case HighBall:
            //     highBall();
            //     break;
        }
        isRunning = false;
    }

    private void hatchPickup()
    {
        setPID(arm, 10, 5000);
        setPID(wrist, 0, 5000);
        setPID(arm, 20, 5000);
    }

    private void lowHatch()
    {
        setPID(wrist, 0, 5000);
        setPID(arm, 30, elevator, 0, 5000);
    }

    private void midHatch()
    {
        setPID(wrist, 0, 5000);
        setPID(arm, 100, elevator, 15, 5000);
    }

    // private void highHatch()
    // {
    //     setPID(wrist, 0, 5000);
    //     setPID(arm, 100, elevator, 170, 5000);
    // }

    public void lowBall()
    {

    }

    public void midBall()
    {

    }

    public void highBall()
    {

    }

    private void setPID(PIDInterface pid, int position, int timeout)
    {
        long startTime = System.currentTimeMillis();

        pid.setPosition(position);
        while(!pid.onTarget(pid.getPosition()))
        {
            if(System.currentTimeMillis() > startTime + timeout)
                break;
        }
    }

    private void setPID(PIDInterface pid, int position, PIDInterface pid2, int position2, int timeout)
    {
        long startTime = System.currentTimeMillis();

        pid.setPosition(position);
        pid2.setPosition(position2);
        while(!pid.onTarget(pid.getPosition()) || !pid2.onTarget(pid2.getPosition()))
        {
            if(System.currentTimeMillis() > startTime + timeout)
                break;
        }
    }

    public static boolean isRunning()
    {
        return isRunning;
    }

    public static void runPreset(Preset preset)
    {
        Presets presets = new Presets(preset);
        presets.start();
    }
}
