/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class DriveTrain extends SubsystemBase {
    /**
     * Creates a new DriveTrainSubsystem.
     */

    private TalonFX frontLeftDriveMotor;
    private TalonFX frontRightDriveMotor;
    private TalonFX rearLeftDriveMotor;
    private TalonFX rearRightDriveMotor;

    public AlphaMotors motorFL;
    public AlphaMotors motorFR;
    public AlphaMotors motorRL;
    public AlphaMotors motorRR;

    public static double isSpeed;
    public static double oldMod;
    
    public double l;
    public double w;
    public double r;
    
    public double deleteMeMore;
    public long ahh;

    
    public double FRAngle = 0;
    public double RRAngle = 0;
    public double FLAngle = 0;
    public double RLAngle = 0;

    public double turnyThingy = 0;

    public DriveTrain() {
        motorFL = new AlphaMotors(2, 1, 12, 10, 0);
        motorFR = new AlphaMotors(4, 3, 18, 17, 1);
        motorRL = new AlphaMotors(6, 5, 14, 13, 2);
        motorRR = new AlphaMotors(8, 7, 16, 15, 3);

     //   frontLeftDriveMotor = new TalonFX(1);
     //   frontRightDriveMotor = new TalonFX(3);
     //   rearLeftDriveMotor = new TalonFX(5);
     //   rearRightDriveMotor = new TalonFX(7);


        isSpeed = 0;
        l = Constants.length;
        w = Constants.width;
        r = Math.sqrt((l * l) + (w * w));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void moveSwerveAxis(double leftX, double leftY, double rightX) {
        leftY*=-1;

        double mod = RobotContainer.modifierSub.bestMod;

        if(RobotContainer.limeValue()){
            turnyThingy = limes();
        }else{
            //creation of a deadzone
            if(rightX>=.05 || rightX<=-.05){
                turnyThingy = rightX;
            }else{
                turnyThingy = 0;
            }
        }

        double a = leftX - turnyThingy * (l / r);
        double b = leftX + turnyThingy * (l / r);
        double d = (leftY - turnyThingy * (w / r));
        double c = (leftY + turnyThingy * (w / r));

        double FRDesiredSpeed = (Math.sqrt((b*b)+(d*d)));
        double RRDesiredSpeed = (Math.sqrt((a*a)+(d*d)));
        double FLDesiredSpeed = (Math.sqrt((b*b)+(c*c))*-1);
        double RLDesiredSpeed = (Math.sqrt((a*a)+(c*c))*-1);


        if(leftX==0 && leftY == 0 && turnyThingy == 0){
            FRAngle = 0;
            FLAngle = 0;
            RRAngle = 0;
            RLAngle = 0;
        }else{
        FRAngle = (Math.atan2(b, d) / Math.PI);
        RRAngle = (Math.atan2(a, d) / Math.PI);
        FLAngle = Math.atan2(b, c) / Math.PI;
        RLAngle = Math.atan2(a, c) / Math.PI;
        }

        motorFL.drive(FLDesiredSpeed, FLAngle, mod);
        motorFR.drive(FRDesiredSpeed, FRAngle, mod);
        motorRR.drive(RRDesiredSpeed, RRAngle, mod);
        motorRL.drive(RLDesiredSpeed, RLAngle, mod);

        ahh = (long) FLAngle;
        deleteMeMore = FLAngle;
    }

    public void zeroAllEncoders() {
        motorFL.zeroEncoder();
        motorFR.zeroEncoder();
        motorRL.zeroEncoder();
        motorRR.zeroEncoder();
    }

    public void findAllZeros() {
        motorFL.findZero();
        motorFR.findZero();
        motorRL.findZero();
        motorRR.findZero();
    }

    public void zeroAllEncodersBasedOnProx() {
        motorFL.zeroEncoderBasedOnProx();
        motorFR.zeroEncoderBasedOnProx();
        motorRL.zeroEncoderBasedOnProx();
        motorRR.zeroEncoderBasedOnProx();
    }

    public double limes(){
        double x = RobotContainer.sensorsSubsystem.x/25;
        double dire = Math.abs(x)/x;
        //point where power starts decreasing
        double tstart = .5;
        //minimum power to turn
        double e = .06;
        //linear term
        double w = 1;
        //exponential variable
        double g = (1-w*tstart-e)/(tstart*tstart);

        return dire*(g*(x*x))+w*x+e*dire;
    }
}

