// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.conveyer;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.storage;

public class GalacticAutoIntake extends CommandBase {
  
  intake m_in;
  storage m_store;
  conveyer m_conveyer;
  double timer;
  boolean flag;
  int ballNum;


  public GalacticAutoIntake(intake subsystem1, storage subsystem2, conveyer subsystem3, int ball) {

    m_in = subsystem1;
    m_store = subsystem2;
    m_conveyer = subsystem3;
    ballNum = ball;

    addRequirements(subsystem1, subsystem2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    timer = 0;
    flag = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    timer += 0.02;

    /*
    Ball number will determine what mechanisms will run 
    and how far into the storage the ball will go to make 
    space for subsequent balls during the auto.
    */
    if (ballNum == 1) {

      m_in.driveIntake(Constants.GALACTIC_AUTO_INTAKE_SPEED);
      m_store.storageDrive(Constants.GALACTIC_AUTO_STORAGE_SPEED);

    } else if (ballNum == 2 || ballNum == 3) {

        m_in.driveIntake(Constants.GALACTIC_AUTO_INTAKE_SPEED);
        m_store.storageDrive(Constants.GALACTIC_AUTO_STORAGE_SPEED);

        if(timer < 1) {
          m_conveyer.deliveryDrive(Constants.GALACTIC_AUTO_DELIVERY_SPEED);
        }

    }
    
    if(timer >= 1) {

      m_conveyer.deliveryDrive(0);

    }


    if(timer >= Constants.GALACTIC_AUTO_INTAKE_RUNTIME) { //Intake runs for 3 seconds (Current constant value) and then command ends
      flag = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_in.driveIntake(0);
    m_store.storageDrive(0);
    m_conveyer.deliveryDrive(0);
    System.out.println("Intake finished");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
