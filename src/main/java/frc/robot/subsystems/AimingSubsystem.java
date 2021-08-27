/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class AimingSubsystem extends SubsystemBase {
    /**
     * Creates a new aimingSubsystem.
     */
    private TalonSRX aimingMotor;

    private double height;

    public boolean ready;

    public AimingSubsystem() {
        aimingMotor = new TalonSRX(11);

        ready = false;
    }

    @Override
    public void periodic() {
        setMinMax();
        // This method will be called once per scheduler run
        height = RobotContainer.sensorsSubsystem.linearEncoderValue;
    }

    public void raiseIntake() {
        if(RobotContainer.sensorsSubsystem.linearEncoder.get() > .49){
        aimingMotor.set(ControlMode.PercentOutput, .5);
     }else{
         aimingMotor.set(ControlMode.PercentOutput, 0);
     }
    }

    public void lowerIntake() {
        if(RobotContainer.sensorsSubsystem.linearEncoder.get() < .96){
            aimingMotor.set(ControlMode.PercentOutput, -.5);
        }else{
            aimingMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setMinMax() {
        aimingMotor.configPeakOutputForward(1);
        aimingMotor.configPeakOutputReverse(-1);
    }

    public void killMotors() {
        aimingMotor.set(ControlMode.PercentOutput, 0);
    }

    //zoneThree and zoneFour put the launcher in the right direction for the right zone 
    public void zoneThree(){
        if(height>= .87 && height<= .89){
            ready = true;
        }else{
            aimingMotor.set(ControlMode.PercentOutput, 
            (height-.88)/Math.abs(height-.88)*.5);
            ready = false;
        }
    }
        
    public void zoneFour(){
        if(height>= .96){
            ready = true;
        }else{
        aimingMotor.set(ControlMode.PercentOutput, -.5);
        ready = false;
        }
    }

    public void zoneOne(){
        if(height>= .96){
            ready = true;
        }else{
           aimingMotor.set(ControlMode.PercentOutput, -.5);
           ready = false; 
        }
    }
    public void zoneTwo(){
        if(height>= .47 && height<= .49){
            ready = true;
        }else{
            aimingMotor.set(ControlMode.PercentOutput, 
            (height-.48)/Math.abs(height-.48)*.5);
            ready = false;
        }
    }

    public void stopMoving(){
        aimingMotor.set(ControlMode.PercentOutput, 0);
    }

    //going to be honest it is 1:30 I'll change completely later 
    // This is a case to get which dpad button you are pressing and it runs a zone method for each case

    public void wayToComplicatedMeansForActuallyDoingThisPleaseHelp(){
        switch(RobotContainer.dpadValue()){
            case 1:
            zoneOne();
            break;
            case 2:
            zoneThree();
            break;
            case 3:
            zoneTwo();
            break;
            case 4:
            zoneFour();
            break;
        }
    }
}
