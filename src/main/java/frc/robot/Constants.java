/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //  Victor SP PWM IDs
    public static final int leftDriveM = 2;
    public static final int leftDriveS = 3;
    public static final int rightDriveM = 0;
    public static final int rightDriveS = 1;
    public static final int leftArm = 4;
    public static final int rightArm = 5;
    public static final int intake = 6;

    //TBD CAN IDs (STORAGE, DELIVERY, OUTTAKE)
    public static final int outtake1 = 0;
    public static final int outtake2 = 3;
    public static final int storage1 = 1;  
    public static final int storage2 = 4;    
    public static final int delivery = 2;

    //DIO
    public static final int leftDriveEncoder1 = 6;
    public static final int leftDriveEncoder2 = 5;
    public static final int rightDriveEncoder1 = 3;
    public static final int rightDriveEncoder2 = 4;

    //Encoder constants
    public static final double pulsesPerRotation = 251.329;
    //4*360 maybe, circumference in inches
    public static final double wheelCircumference = 3*2*Math.PI;

    //Drive constants
    public static final double minDrive = 0.6;
    public static final double minTurn = 0.6;
    
    //Limelight constants for getDistance() 
    public static final double MOUNTING_ANGLE = -4.416; //Angle must be negative if camera angle is below the horizontal and positive if not
    public static final double LIME_HEIGHT = 13; //In inches. Used for measuring distance to target below limelight
    public static final double HEIGHT_OF_TARGET = 0; //In inches. Used for measuring distance to target above limelight
    
    //Intake & Storage Auto constants (GALACTIC SEARCH AUTO INTAKE)
    public static final double GALACTIC_AUTO_INTAKE_SPEED = 0.75;
    public static final double GALACTIC_AUTO_STORAGE_SPEED = 0.75;
    public static final double GALACTIC_AUTO_DELIVERY_SPEED = 0.75;
    public static final double GALACTIC_AUTO_INTAKE_RUNTIME = 3; //(in seconds)

    //Detect speed (GALACTIC SEARCH AUTO DETECT)
    public static final double GALACTIC_AUTO_DETECT_ROTATION_SPEED = -0.5;
    
    //Xbox controller ports
	public static final int XBOX_CONTROLLER_LEFT_Y_AXIS = 1;
	public static final int XBOX_CONTROLLER_RIGHT_X_AXIS = 4;
	
}
