/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTableInstance;

public class storage extends SubsystemBase {
  private WPI_VictorSPX m_storage1;
  private WPI_VictorSPX m_storage2;
  private double maxStorage = 1;
  private double direction;

  public storage() {
    direction = 0;
    m_storage1 = new WPI_VictorSPX(Constants.storage1);
    m_storage2 = new WPI_VictorSPX(Constants.storage2);
  }

  public void storageDrive(double speed){
    direction = speed;
    m_storage1.set(-speed*maxStorage);
    m_storage2.set(speed*maxStorage);
  }

  public double getDirection(){
    return direction;
  }

  @Override
  public void periodic() {
    // maxStorage = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxStorage").getDouble(0.0);

  }
}
