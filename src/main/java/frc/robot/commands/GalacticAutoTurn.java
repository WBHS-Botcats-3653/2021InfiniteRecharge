// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
import frc.robot.subsystems.limelight;

public class GalacticAutoTurn extends CommandBase {

  limelight m_lime;
  drive m_drive;
  driveSensors m_gyro;
  double target;
  boolean flag;
  double kP;
  double kI;
  double kD;
  double angle;
  double tx;
  double direction;
  double difference;
  double error;
  double proportional;
  double integral;
  double derivative;
  double turnspeed;
  double prev_error;
  double prev_angle;
  double ep;
  boolean cumulativeRun;

  /** Creates a new GalacticAuto. */
  public GalacticAutoTurn(drive subsystem1, limelight subsystem2, driveSensors subsystem3, boolean isCumulativeRun) {
    m_drive = subsystem1;
    m_lime = subsystem2;
    m_gyro = subsystem3;
    kP = 0.7;
    kD = 1.25;
    kI = 0.00455;
    ep = 0.005;
    cumulativeRun = isCumulativeRun;
    addRequirements(subsystem1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //Sets the target for the auto turn
    if(cumulativeRun) {

      target = m_gyro.getCumulativeAngle(); 

      if(target>180) { //Attempt to have robot find the shortest angle to turn to reset

        target -= 360; 

      } else if(target < -180) {

        target += 360;

      }

    } else {

      target = m_lime.getX();

    } 

    flag = false;
    m_gyro.resetGyro();
    proportional = 0;
    integral = 0;
    derivative = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    angle = m_gyro.getAngle();  
    m_gyro.accumulateAngle(angle - prev_angle); //Angle accumulation for reset

    difference = Math.abs(target - angle);

    if(cumulativeRun) {

      direction = (angle - target)/difference;
      error = difference/Math.abs(target);
    } else {

      tx = -m_lime.getX(); //negative because turning counterclockwise is positive, but limelight is opposite
      direction = tx/Math.abs(tx);
      error = difference/30;

    } 
    
    proportional = error;
    integral += error;
    derivative = error - prev_error;

    turnspeed = direction * (kP * proportional + kI * integral + kD * derivative);

    m_drive.differentialDrive(0, turnspeed);

    //flag conditions

    if(cumulativeRun) {

      //If angle is within 1 degree of Power Cell and error didn't change (ep = 0), command ends
      if(difference < 1.2 && Math.abs(derivative) <= ep) {

        flag = true;

      }

    } else {

      //If angle is within 1 degree of Power Cell and error didn't change (ep = 0), command ends
      if(Math.abs(m_lime.getX()) < 1 && Math.abs(derivative) <= ep) {

        flag = true;

      }

    } 

    SmartDashboard.putNumber("GalacticAutoTurn difference", difference);
    SmartDashboard.putNumber("GalacticAutoTurn error", error);
    SmartDashboard.putNumber("GalacticAutoTurn Speed", turnspeed);
    SmartDashboard.putNumber("Direction", direction);
    SmartDashboard.putNumber("Raw Difference (Input Target Auto)", angle - target);
    SmartDashboard.putNumber("Proportional", proportional);
    SmartDashboard.putNumber("Derivative", derivative);
    SmartDashboard.putNumber("Integral", integral);


    prev_angle = angle;
    prev_error = error;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drive.differentialDrive(0, 0);
    System.out.println("Turn finished");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
