// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.limelight;

public class GalacticAutoDetect extends CommandBase {
  
  drive m_drive;
  limelight m_lime;
  boolean flag;

  public GalacticAutoDetect(drive subsystem1, limelight subsystem2) {
    addRequirements(subsystem1);

    m_drive = subsystem1;
    m_lime = subsystem2;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    flag = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    m_drive.differentialDrive(0, Constants.GALACTIC_AUTO_DETECT_ROTATION_SPEED);

    if(m_lime.validTarget()) {

      flag = true;

    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
