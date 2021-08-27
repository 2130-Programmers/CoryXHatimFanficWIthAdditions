/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Dpad;
import frc.robot.RobotContainer;

public class LauncherSubsystem extends SubsystemBase {
  /**
   * Creates a new LauncherSubsystem.
   */
  public CANSparkMax launcherMotorMaster;
  public CANSparkMax launcherMoterSlave;

  public double finalSpeed;
  public boolean active;
  public boolean inZoneFour;

  public LauncherSubsystem() {
    

    launcherMotorMaster = new CANSparkMax(2, MotorType.kBrushless);
    launcherMoterSlave = new CANSparkMax(1, MotorType.kBrushless);

    finalSpeed = 0;
    active = false;
    inZoneFour = false;

    setMaster();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
  

  public void setMaster(){
    launcherMoterSlave.follow(launcherMotorMaster);
  }

  // public void widndUp(){
  //   if (finalSpeed <= 1){
  //   finalSpeed +=.01;
  //   launcherMotorMaster.set(finalSpeed);
  //   }else{
  //     launcherMotorMaster.set(finalSpeed);
  //   }
  // }

  public void windDown(){
    finalSpeed = .0;
    launcherMotorMaster.set(0);
  }

  public void speedReset(){
    finalSpeed = 0;
  }

  public void windUp(){
    if(inZoneFour){
      if (finalSpeed <= .87){
        finalSpeed +=.01;
      }
    }else{
      if (finalSpeed <= 1){
        finalSpeed +=.01;
        }
    }
    launcherMotorMaster.set(finalSpeed);
  }

  public void zoneFourBool(){
    if(RobotContainer.dpadValue()==4){
      inZoneFour=true;
    }else{
      inZoneFour=false;
    }
  }
}
