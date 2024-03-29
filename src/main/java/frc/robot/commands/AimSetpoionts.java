// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AimingSubsystem;

public class AimSetpoionts extends CommandBase {
  /** Creates a new AimSetpoionts. */
  private AimingSubsystem aimingSubsystem;
  public AimSetpoionts(AimingSubsystem tempSub) {
    aimingSubsystem = tempSub;
    addRequirements(tempSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.aimingSubsystem.wayToComplicatedMeansForActuallyDoingThisPleaseHelp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    aimingSubsystem.stopMoving();
    RobotContainer.launcherSubsystem.zoneFourBool();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.aimingSubsystem.ready;
  }
}
