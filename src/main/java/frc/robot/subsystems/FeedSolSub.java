// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class FeedSolSub extends SubsystemBase {
  /** Creates a new FeedSolSub. */
  
  public boolean feedSolMethodIsWorking = false;
  private Solenoid feedSol;
  
  public FeedSolSub() {
    
      feedSol = new Solenoid(1);
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
    

    public void releaseBalls() {
      feedSol.set(true);
  }

  public void tightenBalls() {
      feedSol.set(false);
  }

  public void masterOfBalls(){
      if(RobotContainer.operatorJoystick.getRawAxis(3)>.8){
          releaseBalls();
      }else{
          tightenBalls();
      }
  }
}
