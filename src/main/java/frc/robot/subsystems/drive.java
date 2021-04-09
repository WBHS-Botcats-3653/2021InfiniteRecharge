/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class drive extends SubsystemBase {
  /**
   * Creates a new drive.
   */  
  public double maxDrive = 1;

  private VictorSP m_leftDriveMaster = null;
  private VictorSP m_leftDriveSlave = null;
  private VictorSP m_rightDriveMaster = null;
  private VictorSP m_rightDriveSlave = null;

  private SpeedControllerGroup m_leftDrive = null;
  private SpeedControllerGroup m_rightDrive = null;

  private DifferentialDrive m_drive = null;

  public drive() {
    m_leftDriveMaster = new VictorSP(Constants.leftDriveM);
    m_rightDriveMaster = new VictorSP(Constants.rightDriveM);
    m_leftDriveSlave = new VictorSP(Constants.leftDriveS);
    m_rightDriveSlave = new VictorSP(Constants.rightDriveS);
    
    // Groups both left controls and both right controllers
    // to have the same values. Inverts one side so it controls
    // properly
    m_leftDrive = new SpeedControllerGroup(m_leftDriveMaster, m_leftDriveSlave);
    m_rightDrive = new SpeedControllerGroup(m_rightDriveMaster, m_rightDriveSlave);
    m_leftDrive.setInverted(true);
    
    // Creates differential drive using motor groups
    m_drive = new DifferentialDrive(m_leftDrive,m_rightDrive);
  }

  public void differentialDrive(double speed, double angle){
    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("rotation", angle);
    m_drive.arcadeDrive(angle, -speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
