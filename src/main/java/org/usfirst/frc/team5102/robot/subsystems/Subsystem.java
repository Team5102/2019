package org.usfirst.frc.team5102.robot.subsystems;

import org.usfirst.frc.team5102.robot.util.DriverStation5102;

public abstract class Subsystem
{
	DriverStation5102 ds = DriverStation5102.getInstance();
	
	void teleopInit() {}
	void teleop() {}
	void autonInit() {}
	void auton() {}
	void testInit() {}
	void test() {}
	void disabledInit() {}
	void disabled() {}
}
