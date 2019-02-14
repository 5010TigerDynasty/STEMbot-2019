/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static OI oi;

  private Joystick driver;
  private double straightValue;
  private double turnValue;
  private double deadZone = 0.15;

  private Spark leftMotor;
  private Spark rightMotor;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    oi = new OI();
    edu.wpi.first.cameraserver.CameraServer.getInstance().startAutomaticCapture();
    leftMotor = new Spark(0);
    rightMotor = new Spark(1);
    driver = new Joystick(0);
  }

  @Override
  public void teleopPeriodic() {
    turnValue = scaleInputs(driver.getRawAxis(4)) * 0.3 ;
		straightValue = -scaleInputs(driver.getRawAxis(1)) * 0.5 ;
		setMotors(turnValue + straightValue, turnValue - straightValue);
  }

  void setMotors(double leftSpeed, double rightSpeed) {
		leftMotor.set(leftSpeed);
		rightMotor.set(rightSpeed);
	}
	
	public double scaleInputs(double input) {
		if (Math.abs(input) < deadZone) {
			input = 0;
		} else if (input > 0) {
			input = (input - deadZone) * 1 / (1 - deadZone);
		} else if (input < 0) {
			input = (input + deadZone) * 1 / (1 - deadZone);
		}
		return input * 1;
	}
}
