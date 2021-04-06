/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.outtake;

public class outtakeEngage extends CommandBase {
  /**
   * Creates a new outtakeEngage.
   */
  private outtake m_out = null;
  private int direction;

  public outtakeEngage(outtake subsystem, int dir) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_out = subsystem;
    direction = dir;
    addRequirements(m_out);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_out.driveOuttake(direction);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_out.driveOuttake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
