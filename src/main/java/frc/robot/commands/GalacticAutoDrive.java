// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
import frc.robot.subsystems.limelight;

public class GalacticAutoDrive extends CommandBase {

  drive m_drive;
  driveSensors m_encoders;
  limelight m_lime;
  double distance;

  public GalacticAutoDrive(drive subsystem1, limelight subsystem2, driveSensors subsystem3) {

    m_drive = subsystem1;
    m_lime = subsystem2;
    m_encoders = subsystem3;

    addRequirements(subsystem1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_encoders.resetEncoders();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {






  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
