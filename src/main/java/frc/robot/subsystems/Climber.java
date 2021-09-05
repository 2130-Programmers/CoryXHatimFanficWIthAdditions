
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  //Creating Hardware

  private TalonSRX climberMotor;
  private Solenoid climberLatch;
  boolean latchSolenoidState;


  /** Creates a new Climber. */
  public Climber() {
    climberMotor = new TalonSRX(9);
    climberLatch = new Solenoid(0);
  }


  //Controlls the solenoid
  public void setLatch(String state) {
    boolean cont;
    if (state=="in") {
      cont = true;
    }else{
      cont = false;
    }
    latchSolenoidState = cont;
    climberLatch.set(cont);
  }

  //Returns the state of the solenoid
  public boolean getLatchState() {
    return latchSolenoidState;
  }

  public void runMotor(String direction) {
    if (direction == "in") {
    climberMotor.set(ControlMode.PercentOutput, 1);
    }else{
    climberMotor.set(ControlMode.PercentOutput, -1);
    }
  }

  public void stopWinch() {
    climberMotor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
