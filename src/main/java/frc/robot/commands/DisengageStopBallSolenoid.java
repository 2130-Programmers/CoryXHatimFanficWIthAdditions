/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.FeedSolSub;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LauncherSubsystem;

public class DisengageStopBallSolenoid extends CommandBase {
    /**
     * Creates a new DisengageStopBallSolenoid.
     */

    private FeedSolSub feedSolSub;

    public DisengageStopBallSolenoid(FeedSolSub feedSolSub) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.feedSolSub = feedSolSub;
        addRequirements(this.feedSolSub);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        feedSolSub.masterOfBalls();
        feedSolSub.feedSolMethodIsWorking = true;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        feedSolSub.feedSolMethodIsWorking = false;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
