/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  
  public int climbMode = 0;
  public static double cumulativeAngle;

  // Initiates options-list for the auto command
  private SendableChooser<Command> autoCommand = new SendableChooser<>();

  // Creates instances for every subsystem and the controller
  private final drive m_drive = new drive();
  private final delivery m_delivery = new delivery();
  private final outtake m_out = new outtake();
  private final intake m_in = new intake();
  private final limelight m_lime = new limelight();
  private final storage m_store = new storage();
  private final driveSensors m_drivesensors = new driveSensors();
  private final XboxController m_controller = new XboxController(0);
  private final Arm m_arm = new Arm();

  public RobotContainer() {

    // Creates the different auto command options 
    
    autoCommand.addOption("Galactic Search Test Turn", new GalacticAutoTurn(m_drive, m_lime, m_drivesensors, false));
    
    autoCommand.addOption("Galactic Search Test Drive", 
      new ParallelCommandGroup(
        new GalacticAutoDrive(m_drive, m_lime, m_drivesensors),
        new GalacticAutoIntake(m_in, m_store, m_delivery, 1)      
       )
    );
    
    autoCommand.addOption("Galactic Search Auto :)", 
    
      new SequentialCommandGroup(
        new ParallelCommandGroup(
          new GalacticAutoDrive(m_drive, m_lime, m_drivesensors),
          new GalacticAutoIntake(m_in, m_store, m_delivery, 1)      
        ),
        new GalacticAutoDetect(m_drive, m_lime, m_drivesensors),
        new GalacticAutoTurn(m_drive, m_lime, m_drivesensors, false),
        new ParallelCommandGroup(
          new GalacticAutoDrive(m_drive, m_lime, m_drivesensors),
          new GalacticAutoIntake(m_in, m_store, m_delivery, 2)      
        ),
        new GalacticAutoDetect(m_drive, m_lime, m_drivesensors),
        new GalacticAutoTurn(m_drive, m_lime, m_drivesensors, false),
        new ParallelCommandGroup(
          new GalacticAutoDrive(m_drive, m_lime, m_drivesensors),
          new GalacticAutoIntake(m_in, m_store, m_delivery, 3)      
        ),
        new GalacticAutoTurn(m_drive, m_lime, m_drivesensors, true),
        new GalacticAutoDrive(m_drive, m_lime, m_drivesensors, 300)
      )    
    
    );


    SmartDashboard.putData("Choose Auto",autoCommand);
    
    // Starts camera and configures resolution/fps
    
    // cam0 = CameraServer.getInstance().startAutomaticCapture(0);
		// cam0.setResolution(72, 45);
		// cam0.setFPS(20);

    // Sets default drive command. Lambdas are used 
    // to pass analog inputs, arithmetic calculates
    // changed speed from climbMode value. 
    m_drive.setDefaultCommand(new differentialDrive(m_drive, m_controller));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    // Trigger deadzone
    double trigEpsilon = 0.3;
    
    // Binds all buttons to trigger instances. Trigger
    // constructor takes a boolean function as a para-
    // meter. Internally it calls this function, and the
    // value returned determines whether to call the
    // supplied command. Usage of :: takes a boolean
    // method from the controller instance.
    Trigger buttonA = new Trigger(m_controller::getAButton);
    Trigger buttonB = new Trigger(m_controller::getBButton);
    Trigger buttonX = new Trigger(m_controller::getXButton);
    Trigger buttonY = new Trigger(m_controller::getYButton);
    
    // Here the numeric values of the bumpers are used
    // to create JoystickButton instances, which act nearly
    // the same as Triggers.
    JoystickButton leftBump = new JoystickButton(m_controller,5);
    JoystickButton rightBump = new JoystickButton(m_controller,6);
    
    // Controller triggers (not the object, but the button)
    // are analog, but are being used here as digital inputs.
    // The conversion is made using lambdas and the previously
    // established deadzone trigEpsilon
    Trigger rightTrig = new Trigger(
      () -> {
             return (m_controller.getTriggerAxis(GenericHID.Hand.kRight)>trigEpsilon);
            }
      );
    Trigger leftTrig = new Trigger(
      () -> {
              return (m_controller.getTriggerAxis(GenericHID.Hand.kLeft)>trigEpsilon);
            }
    );

    //Button mapping to commands

    buttonB.whileActiveOnce(new intakeEngage(m_in, 1));
    buttonA.whileActiveOnce(new intakeEngage(m_in, -1));
    buttonB.whileActiveOnce(new storageEngage(m_store, 1));
    buttonY.whileActiveOnce(new deliveryEngage(m_delivery, -1));
    leftTrig.whileActiveOnce(new armEngage(m_arm, 1));
    leftBump.whileActiveOnce(new armEngage(m_arm, -1));
    rightTrig.whileActiveOnce(new outtakeEngage(m_out, 1));

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand(){
    return autoCommand.getSelected();
  }

}
