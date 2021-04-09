// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
import frc.robot.subsystems.limelight;

public class GalacticAutoDetect extends CommandBase {
  
  drive m_drive;
  limelight m_lime;
  driveSensors m_gyro;
  boolean flag;
  double timer;
  double angle;
  double prev_angle;
  double rotationSpeed;

  public GalacticAutoDetect(drive subsystem1, limelight subsystem2, driveSensors subsystem3) {
    addRequirements(subsystem1);

    m_drive = subsystem1;
    m_lime = subsystem2;
    m_gyro = subsystem3;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    flag = false;
    timer = 0;
    m_gyro.resetGyro();
    rotationSpeed = 0;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    timer += 0.02;
    
    angle = m_gyro.getAngle();
    m_gyro.accumulateAngle(angle - prev_angle); //Angle accumulation for reset

    if(!m_lime.validTarget()) {

      rotationSpeed = Constants.GALACTIC_AUTO_DETECT_ROTATION_SPEED;

    } else {

      rotationSpeed = 0;

    }

    m_drive.differentialDrive(0, rotationSpeed);

    if(m_lime.validTarget() && timer > 2) {

      flag = true;

    }

    prev_angle = angle;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drive.differentialDrive(0,0);
    System.out.println("Detect finished");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
