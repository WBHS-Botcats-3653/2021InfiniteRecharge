/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight extends SubsystemBase {
  //initiates networktable variables

  private NetworkTable table = null;
  private NetworkTableEntry tx = null;
  private NetworkTableEntry ty = null;
  private NetworkTableEntry tv = null;


  public limelight() {

  }

  //functions to grab limelight metrics
  public double getX(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    
  }

  public double getY(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
  }

  public double getWidth(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0.0);
  }

  public boolean validTarget(){
    return ((double) NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getNumber(0)) != 0.0;
  }

  //changes visionpipeline between ball aiming and target aiming
  public void changePipe(){
    NetworkTableEntry pipeNum = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline");
    if(pipeNum.getDouble(0) == 0){
      pipeNum.setNumber(1);
    }
    else{
      pipeNum.setNumber(0);
    }
  }

  //changes vision mode between processing and driver cam
  public void changeMode(){
    NetworkTableEntry camMode = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode");
    if(camMode.getDouble(0) == 0){
      camMode.setNumber(1);
    }
    else{
      camMode.setNumber(0);
    }
  }
  
  //Calculates the distance to the Power Cell. Converts the calculated degrees to radians for the
  //tangent function to work properly
  public double getDistance(){
    
    double distance = 0;  

    double angle_to_target = getY() + Constants.MOUNTING_ANGLE; //Calculates angle to target on the horizontal

    if(getY() < 0) {

      distance = Math.abs(Constants.LIME_HEIGHT / Math.tan(Math.toRadians(angle_to_target)));

    }

    if (getY() > 0) {

      distance = Constants.HEIGHT_OF_TARGET / Math.tan(Math.toRadians(angle_to_target));

    }

    return distance;      
  }

  public double getShooterDistance(){


    return 0.0;
  }

  public double getPerfectSpeed(){
    return 1;
  }

  @Override
  public void periodic() {
    // Sends the values of the limelight to SmartDashboard
    SmartDashboard.putNumber("targetX", getX());
    SmartDashboard.putNumber("targetY", getY());
    SmartDashboard.putNumber("targetW", getWidth());
    SmartDashboard.putBoolean("targetValid", validTarget());
    SmartDashboard.putNumber("distance according to lime", getShooterDistance());
  }
}
