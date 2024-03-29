/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {  
  // The robot's subsystems and commands are defined here...
  public static int lastPressed = 0;

  public final static DriveTrain driveTrain = new DriveTrain();
  public final static ModifierSub modifierSub = new ModifierSub();
  public final static LauncherSubsystem launcherSubsystem = new LauncherSubsystem();
  public final static SensorsSubsystem sensorsSubsystem = new SensorsSubsystem();
  public final static AimingSubsystem aimingSubsystem = new AimingSubsystem();
  public final static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public final static FeedSolSub feedSolSub = new FeedSolSub();
  public final static Climber climber = new Climber();

  private final PlsWork plsWork = new PlsWork(driveTrain);
  private final ModChanger modChanger = new ModChanger(modifierSub);
  private final HeresTheWindUp heresTheWindUp = new HeresTheWindUp(launcherSubsystem);
  private final AimingCommand aimingCommand = new AimingCommand(aimingSubsystem);
  private final AimingCommandDown aimingCommandDown = new AimingCommandDown(aimingSubsystem);
  private final DisengageStopBallSolenoid disengageStopBallSolenoid = new DisengageStopBallSolenoid(feedSolSub);
  private final AimSetpoionts aimSetpoionts = new AimSetpoionts(aimingSubsystem);
  private final SwitchPipeCommand switchPipeCommand = new SwitchPipeCommand(sensorsSubsystem);
  private final FlopIntakeInCommand flopIntakeInCommand = new FlopIntakeInCommand(intakeSubsystem);
  private final FlopIntakeOutCommand flopIntakeOutCommand = new FlopIntakeOutCommand(intakeSubsystem);
  private final flopIntakeOutForAutonomous flopIntakeOutForAutonomous = new flopIntakeOutForAutonomous(intakeSubsystem);
  private final ToggleClimberLatch toggleClimberLatch = new ToggleClimberLatch(climber);
  private final ContractClimber contractClimber = new ContractClimber(climber);  
  private final ReleaseClimber releaseClimber = new ReleaseClimber(climber);
  private final StopClimberWinch stopClimberWinch = new StopClimberWinch(climber);

  private final TurnTest turnTest = new TurnTest(driveTrain);

  public static final ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  /**
   * The Driver Joystick declaration and the button definitions associated with
   * it.
   */

  public static final Joystick driverJoystick = new Joystick(0);

  private final static JoystickButton aimWithALime = new JoystickButton(driverJoystick, Constants.driverButtonLB);
  private final static JoystickButton switchPipelines = new JoystickButton(driverJoystick, Constants.driverButtonStart);
  private final static JoystickButton circleTest = new JoystickButton(driverJoystick, Constants.driverButtonBack);
  /**
   * The Operator Joystick declaration and the button definitions associated with
   * it.
   */

  public static final Joystick operatorJoystick = new Joystick(1);

  private final static JoystickButton changeHandlerPositionButton = new JoystickButton(operatorJoystick, Constants.operatorButtonRightJoyClick);
  private final static JoystickButton windLauncherUpButton = new JoystickButton(operatorJoystick, Constants.operatorButtonX);
  private final static JoystickButton windLauncherDownButton = new JoystickButton(operatorJoystick, Constants.operatorButtonY);
  private final static JoystickButton lowerLauncherButton = new JoystickButton(operatorJoystick, Constants.operatorButtonLB);
  private final static JoystickButton raiseLauncherButton = new JoystickButton(operatorJoystick, Constants.operatorButtonRB);
  private final static JoystickButton disengageStopBallSoneloidButton = new JoystickButton(operatorJoystick, Constants.operatorButtonLeftJoyClick);
  private final static JoystickButton flopIntakeInButton = new JoystickButton(operatorJoystick, Constants.operatorButtonA);
  private final static JoystickButton flopIntakeOutButton = new JoystickButton(operatorJoystick, Constants.operatorButtonB);
  private final static JoystickButton backButton = new JoystickButton(driverJoystick, Constants.driverButtonBack);
  private final static JoystickButton runWinchInButton = new JoystickButton(driverJoystick, Constants.driverButtonLB);
  private final static JoystickButton runWinchOutButton = new JoystickButton(driverJoystick, Constants.driverButtonRB);

  // cardinal directions on the dpad and they work in angles starting with 0 on
  // top
  private final static POVButton zoneThree = new POVButton(operatorJoystick, 90);
  private final static POVButton zoneFour = new POVButton(operatorJoystick, 270);
  private final static POVButton bottomZone = new POVButton(operatorJoystick, 180);
  private final static POVButton up = new POVButton(operatorJoystick, 0);

  /*
   * Dpad is a class I created that serves as a button that activats when any of
   * the documented direction of the dpad is pressed It is loacted below constants
   */
  private final static Dpad dpad = new Dpad(up, bottomZone, zoneFour, zoneThree);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveTrain.setDefaultCommand(plsWork);
    modifierSub.setDefaultCommand(modChanger);
    feedSolSub.setDefaultCommand(disengageStopBallSolenoid);
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    backButton.whenPressed(toggleClimberLatch, true);

    runWinchInButton.whenPressed(contractClimber, true);
    runWinchOutButton.whenPressed(releaseClimber, true);

    runWinchInButton.whenReleased(stopClimberWinch, true);
    runWinchOutButton.whenReleased(stopClimberWinch, true);

    windLauncherUpButton.whenPressed(heresTheWindUp, true);

    raiseLauncherButton.whenPressed(aimingCommand, true);
    lowerLauncherButton.whenPressed(aimingCommandDown, true);

    flopIntakeInButton.whenPressed(flopIntakeInCommand, true);
    flopIntakeOutButton.whenPressed(flopIntakeOutCommand, true);

    disengageStopBallSoneloidButton.whenPressed(disengageStopBallSolenoid, true);

    dpad.whenPressed(aimSetpoionts, true);

    zoneThree.whenPressed(aimSetpoionts, true);
    zoneFour.whenPressed(aimSetpoionts, true);
    up.whenPressed(aimSetpoionts, true);
    bottomZone.whenPressed(aimSetpoionts, true);

    switchPipelines.whenPressed(switchPipeCommand, true);
    //circleTest.whenPressed(turnTest, true);<----------------------------------------------- Commented out because it was a test and no longer needed
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

  public static double getDriverAxis(int axis) {
    if (axis == 1 || axis == 5) {
      return -driverJoystick.getRawAxis(axis);
    } else {
      return driverJoystick.getRawAxis(axis);
    }
  }

  public static double getOperatorAxis(int axis) {
    if (axis == 1 || axis == 5) {
      return -operatorJoystick.getRawAxis(axis);
    } else {
      return operatorJoystick.getRawAxis(axis);
    }
  }

  public static boolean limeValue() {
    return aimWithALime.get();
  }

  // operator button values

  public static boolean handlerPositionValue() {
    return changeHandlerPositionButton.get();
  }

  public static boolean launcherButVal() {
    return windLauncherUpButton.get();
  }

  public static boolean stopWindin() {
    return windLauncherDownButton.get();
  }

  public static boolean lowerButVal() {
    return lowerLauncherButton.get();
  }

  public static boolean raiseButVal() {
    return raiseLauncherButton.get();
  }

  public static boolean disengageStopBallSoneloidButtonValue() {
    return disengageStopBallSoneloidButton.get();
  }

  // returns which dpad side is getting pressed.
  public static int dpadValue() {

    if (up.get()) {
      lastPressed = 1;
        }else if(bottomZone.get()){
          lastPressed = 2;
        }else if(zoneThree.get()){
          lastPressed = 4;
        }else if(zoneFour.get()){
          lastPressed = 3;
        }
        return lastPressed;
      }

}

