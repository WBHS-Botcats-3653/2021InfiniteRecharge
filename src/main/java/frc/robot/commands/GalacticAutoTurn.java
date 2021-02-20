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
  double angle;
  double tx;
  double direction;
  double difference;
  double error;
  double turnspeed;

  /** Creates a new GalacticAuto. */
  public GalacticAutoTurn(drive subsystem1, limelight subsystem2, driveSensors subsystem3) {
    m_drive = subsystem1;
    m_lime = subsystem2;
    m_gyro = subsystem3;
    kP = 0.7;
    addRequirements(subsystem1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    flag = false;
    m_gyro.resetGyro();
    target = Math.abs(m_lime.getX());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    angle = Math.abs(m_gyro.getAngle());  
    tx = -m_lime.getX(); //negative because turning counterclockwise is positive, but limelight is opposite
    direction = tx/Math.abs(tx);
    difference = Math.abs(target - angle);
    error = difference/target;
    turnspeed = direction * error * kP;

    m_drive.differentialDrive(0, turnspeed);

    //If angle is within 1 degree of Power Cell, command ends
    if(difference < 1) {

      flag = true;

    }

    SmartDashboard.putNumber("GalacticAutoTurn difference", difference);
    SmartDashboard.putNumber("GalacticAutoTurn error", error);
    SmartDashboard.putNumber("GalacticAutoTurn Speed", turnspeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drive.differentialDrive(0, 0);
    System.out.println("Turn Finished");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
