package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.Presets.Preset;
import org.usfirst.frc.team5102.robot.subsystems.Arm;
import org.usfirst.frc.team5102.robot.subsystems.Elevator;
import org.usfirst.frc.team5102.robot.subsystems.Wrist;
import org.usfirst.frc.team5102.robot.util.DriverStation5102;

public class PresetManager
{
    private static DriverStation5102 ds = DriverStation5102.getInstance();
    private static Elevator elevator = Elevator.getInstance();
    private static Arm arm = Arm.getInstance();
    private static Wrist wrist = Wrist.getInstance();

    public enum HeightMode
    {
        Hatch,
        Ball
    }

    private static HeightMode heightMode = HeightMode.Hatch;

    public static void teleop()
    {
        if(ds.getSecondaryController().getBackButton())
        {
            heightMode = HeightMode.Ball;
            ds.getSecondaryController().rumble(250);
        }
        else if(ds.getSecondaryController().getStartButton())
        {
            heightMode = HeightMode.Hatch;
            ds.getSecondaryController().rumble(250);
        }
            

        if(!Presets.isRunning())
        {
            if(heightMode.equals(HeightMode.Hatch))
            {
                if(ds.getSecondaryController().getPOV() == 180)
                    Presets.runPreset(Preset.LowHatch);
                if(ds.getSecondaryController().getPOV() == 270)
                    Presets.runPreset(Preset.MidHatch);
                if(ds.getSecondaryController().getPOV() == 0)
                    Presets.runPreset(Preset.HighHatch);
            }
            else if(heightMode.equals(HeightMode.Ball))
            {
                if(ds.getSecondaryController().getPOV() == 180)
                    Presets.runPreset(Preset.LowBall);
                if(ds.getSecondaryController().getPOV() == 270)
                    Presets.runPreset(Preset.MidBall);
                if(ds.getSecondaryController().getPOV() == 0)
                    Presets.runPreset(Preset.HighBall);
            }
        }
    }

    public static HeightMode getMode()
    {
        return heightMode;
    }
}
