/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
public class autoDrive extends CommandBase {
  /**
   * Creates a new autoDrive.
   */
  drive m_drive = null;
  driveSensors m_encoders = null;
  double distance;
  double direction;
  boolean flag;
  double heading_stable;
  
  public autoDrive(drive subsystem1, driveSensors subsystem2, double dist) {
    flag = false;
    m_drive = subsystem1;
    m_encoders = subsystem2;
    distance = dist;
    direction = distance/Math.abs(distance);
    heading_stable = 0;
    addRequirements(m_drive);
    
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_encoders.resetEncoders();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(Math.abs(m_encoders.getLeftDistance())<Math.abs(distance)){
      m_drive.differentialDrive(Constants.autoDrive*direction,heading_stable);
    } else {
      flag = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.differentialDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
