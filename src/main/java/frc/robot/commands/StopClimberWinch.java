// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class StopClimberWinch extends CommandBase {
  /** Creates a new StopClimberWinch. */

  boolean done;

  Climber climber;
  public StopClimberWinch(Climber tempSub) {
    climber = tempSub;
    addRequirements(tempSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.stopWinch();
    done = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
