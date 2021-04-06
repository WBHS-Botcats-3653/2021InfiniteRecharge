// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  
  private VictorSP m_leftArm;
  private VictorSP m_rightArm;

  public Arm() {

    m_leftArm = new VictorSP(Constants.leftArm);
    m_rightArm = new VictorSP(Constants.rightArm);

  }

  public void driveArm(double speed) {

    m_leftArm.set(-speed);
    m_rightArm.set(speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
