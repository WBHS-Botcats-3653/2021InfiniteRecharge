/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;

public class differentialDrive extends CommandBase {
  /**
   * Creates a new differentialDrive.
   */
  private final drive m_drive;
  private final XboxController m_controller;

  public differentialDrive(drive subsystem, XboxController controller) {
    m_drive = subsystem;
    m_controller = controller;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // activates drive with controller input
    m_drive.differentialDrive(m_controller.getRawAxis(Constants.XBOX_CONTROLLER_LEFT_Y_AXIS), m_controller.getRawAxis(Constants.XBOX_CONTROLLER_RIGHT_Y_AXIS));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.differentialDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
