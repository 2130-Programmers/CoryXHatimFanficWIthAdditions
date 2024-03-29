/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class PlsWork extends CommandBase {

    private DriveTrain driveTrain;

    /**
     * Creates a new driveSwerveCommand.
     */
    public PlsWork(DriveTrain driveTrain) {
        // Use addRequirements() here to declare subsystem dependencies.

        this.driveTrain = driveTrain;
        addRequirements(this.driveTrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
         driveTrain.moveSwerveAxis(RobotContainer.getDriverAxis(0),
                 RobotContainer.driverJoystick.getRawAxis(1),
                 RobotContainer.getDriverAxis(4));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
