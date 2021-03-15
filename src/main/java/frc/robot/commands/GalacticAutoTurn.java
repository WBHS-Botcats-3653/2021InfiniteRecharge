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
  double inputTarget;
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

  /** Creates a new GalacticAuto. */
  public GalacticAutoTurn(drive subsystem1, limelight subsystem2, driveSensors subsystem3) {
    m_drive = subsystem1;
    m_lime = subsystem2;
    m_gyro = subsystem3;
    kP = 1.6;
    kD = 1.6;
    kI = 0.00255;
    ep = 0.00005;
    addRequirements(subsystem1);
  }

  //second constructor to make the robot turn to a specified angle
  public GalacticAutoTurn(drive subsystem1, limelight subsystem2, driveSensors subsystem3, double inputTarg) {
    m_drive = subsystem1;
    m_lime = subsystem2;
    m_gyro = subsystem3;
    kP = 1.6;
    kD = 1.6;
    kI = 0.00255;
    ep = 0.00005;
    inputTarget = inputTarg;
    addRequirements(subsystem1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //Sets the target for the auto turn

    if(inputTarget != 0.0) {

      target = inputTarget;

    } else {

      target = m_lime.getX();

    } 

    flag = false;
    m_gyro.resetGyro();
    proportional = 0;
    integral = 0;
    derivative = 0;
    kI = 0.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    angle = m_gyro.getAngle();  
    tx = -m_lime.getX(); //negative because turning counterclockwise is positive, but limelight is opposite
    direction = tx/Math.abs(tx);
    difference = Math.abs(target - angle);
    error = difference/30;
    proportional = error;
    integral += error;
    derivative = error - prev_error;

    if(!(derivative > -0.000005 && derivative < 0.000005)) {

    turnspeed = direction * (kP * proportional + kI * integral + kD * derivative);

    m_drive.differentialDrive(0, turnspeed);

    } else {
      m_drive.differentialDrive(0, direction * 0.75);
    }

    //If angle is within 1 degree of Power Cell and error didn't change (ep = 0), command ends
    if(Math.abs(m_lime.getX()) < 1 && Math.abs(derivative) <= ep) {

      flag = true;

    }

    SmartDashboard.putNumber("GalacticAutoTurn difference", difference);
    SmartDashboard.putNumber("GalacticAutoTurn error", error);
    SmartDashboard.putNumber("GalacticAutoTurn Speed", turnspeed);
    SmartDashboard.putNumber("Derivative", derivative);
    SmartDashboard.putNumber("Integral", kI);
    m_gyro.accumulateAngle(angle - prev_angle);

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
