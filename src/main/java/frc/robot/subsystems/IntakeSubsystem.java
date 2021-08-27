/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class IntakeSubsystem extends SubsystemBase {
    /**
     * Creates a new IntakeSubsystem.
     */

    private TalonSRX intakeMotor;

    private DoubleSolenoid intakeSol;

    private boolean oof = true;


    public IntakeSubsystem() {

        intakeMotor = new TalonSRX(10);

        intakeSol = new DoubleSolenoid(3, 4);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void runIntake() {
        if(oof){
            intakeMotor.set(ControlMode.PercentOutput, RobotContainer.getOperatorAxis(0));
        }
    }

    public void stopIntake() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void flopIntakeOut() {
        intakeSol.set(Value.kForward);
        oof = true;
    }

    public void flopIntakeIn() {
        intakeSol.set(Value.kReverse);
        oof = false;
    }

    public void leaveIntakeNeutral() {
        intakeSol.set(Value.kOff);
    }

}
