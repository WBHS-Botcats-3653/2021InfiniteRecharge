// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
import frc.robot.subsystems.limelight;

public class GalacticAutoDrive extends CommandBase {

  drive m_drive;
  driveSensors m_encoders;
  limelight m_lime;
  double distance;
  double inputDist;
  double mounting_angle;
  double lime_height;
  double angle_to_target;
  double error;
  double difference;
  boolean flag;
  double kP;

  public GalacticAutoDrive(drive subsystem1, limelight subsystem2, driveSensors subsystem3) {

    m_drive = subsystem1;
    m_lime = subsystem2;
    m_encoders = subsystem3;
    kP = 1.45;

    addRequirements(subsystem1);
  }

  public GalacticAutoDrive(drive subsystem1, limelight subsystem2, driveSensors subsystem3, double setDistance) {

    m_drive = subsystem1;
    m_lime = subsystem2;
    m_encoders = subsystem3;
    kP = 1.15;
    inputDist = setDistance;

    addRequirements(subsystem1);
  }

  @Override
  public void initialize() {

    if(inputDist != 0.0) {

      distance = inputDist;

    } else {

      distance = m_lime.getDistance();
      SmartDashboard.putNumber("Power Cell Dist.", distance);

    }

    m_encoders.resetEncoders();
    flag = false;

  }

  @Override
  public void execute() {

    difference = distance - m_encoders.getRightDistance();
    error = difference/distance;

    if(difference < 0) {

      flag = true;

    }

    //Given a minimum value of 0.25 so that drive does not stop until flagged
    m_drive.differentialDrive(error * kP + 0.25, 0);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drive.differentialDrive(0, 0);
    System.out.println("Drive finished");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
