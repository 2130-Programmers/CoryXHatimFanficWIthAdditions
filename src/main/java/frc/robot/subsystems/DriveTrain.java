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

import edu.wpi.first.wpilibj.interfaces.Gyro;
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
    public double toTheLane = 0;

    public double circleX = 0;
    public boolean circleDone = false;
    public double capture = 0;
    public boolean swath = true;
    public double swathCap = 1;

    public double invertTwo;
    

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
        leftY*=-invert(leftY, leftX);
        leftX*=invert(leftY, leftX);
        rightX*=invert(leftY, leftX);
        double mod = RobotContainer.modifierSub.bestMod;

        invertTwo = invert(leftY, leftX);

        if(RobotContainer.limeValue()){
             turnyThingy = limes()*invert(leftY, leftX);
             RobotContainer.gyro.reset();
        }else{
            //creation of a deadzone
            if(Math.abs(rightX)>= .05){
                turnyThingy = rightX;
                RobotContainer.gyro.reset();
            }else{
                turnyThingy = gyration()*invert(leftY, leftX); 
            }
            toTheLane = leftX;
        }

        // a b c and d are all sides of the robot and creates wheels as the sides. FR wheel is wheel B D for example
        double a = leftX - turnyThingy * (l / r);
        double b = leftX + turnyThingy * (l / r);
        double d = (leftY - turnyThingy * (w / r));
        double c = (leftY + turnyThingy * (w / r));

        //calculates the speed based on the vector of where side x wants to go to where y does
        double FRDesiredSpeed = (Math.sqrt((b*b)+(d*d)))*invert(leftY, leftX);
        double RRDesiredSpeed = (Math.sqrt((a*a)+(d*d)))*invert(leftY, leftX);
        double FLDesiredSpeed = (Math.sqrt((b*b)+(c*c))*-1)*invert(leftY, leftX);
        double RLDesiredSpeed = (Math.sqrt((a*a)+(c*c))*-1)*invert(leftY, leftX);

        //      This causes big probelems with zeroing because the values are still returning some random value for certain cases and zeroing doesn't occure properly
        //                           \/
        // atan2 returns null essentially when the values are zero so we check for that here
        if(leftX == 0  && leftY == 0 && turnyThingy == 0){
            FRAngle = 0;
            FLAngle = 0;
            RRAngle = 0;
            RLAngle = 0;
        }else{
            //calculating the angles based off of side x and y 
            FRAngle = (Math.atan2(b, d) / Math.PI);
            RRAngle = (Math.atan2(a, d) / Math.PI);
            FLAngle = Math.atan2(b, c) / Math.PI;
            RLAngle = Math.atan2(a, c) / Math.PI;
        }

        // puts the values into the AlphaMotor Class which is defined as a motor 
        //Puts these three parameters into a function called drive
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



    // driving with limelight
    public double limes(){
        double x = RobotContainer.sensorsSubsystem.x/25;

        double dire = (Math.abs(x)/x)*invertTwo;
        //point where power starts decreasing
        double tstart = .8;
        //minimum power to turn
        double c = .03;
        //linear term
        double b = .5;
        //exponential variable
        double a = (.8-b*tstart-c)/(tstart*tstart);

        return (dire*(a*(x*x))+b*x+c*dire);
    }



    //attempting to correct for drift while driving using gryo
    public double gyration(){
        double gyro = RobotContainer.gyro.getAngle();
      
        if(Math.abs(gyro)/70>= .08){
            return gyro/-70; 
        }else{
            return 0;
        }

    }

    /**
    * @param leftOrRight - (boolean) If you are going right put a true and if the direction is left put a false
   */
    public void circle(boolean leftOrRight){
        double angle = 0;
        if(circleX<=1.5){
            circleX+=.008;
            circleDone = false;
            angle = ((.124*Math.sin(14.2*circleX+1.6)+1.8*circleX-.2)/1.6);
        }else{
            circleX = 0;
            circleDone = true;
        }

        if(leftOrRight == false){
            angle = Math.abs(angle-2);
        }

        motorFL.drive(.35*-1, angle, 1);
        motorFR.drive(.35, angle, 1);
        motorRR.drive(.35, angle, 1);
        motorRL.drive(.35*-1, angle, 1);
    }
    
    public double align(){
        double alignment = Math.abs(RobotContainer.sensorsSubsystem.offset);


        if(alignment < 88 && alignment > 60){
            return .5;
        }else if(alignment > 2 && alignment < 60){
            return -.5;
        }else{
            return 0;
        }
    }

//This portion of the code makes it so when you press back on the joystick it will
//go backwards instead of flipping the wheels all the way around.
    public double invert(double axis, double otherAxis){
       // This checks if the joystick is within a small range to see where it is going
            if(.06>Math.abs(axis) && .06> Math.abs(otherAxis)){
                capture = axis+.0000001;
                //swath makes it run once
                swath = true;
                return 1;
            }else{
                if(swath){
                    if(capture > 0){
                        swath = false;
                        swathCap = 1;
                    }else{
                        swath = false;
                        swathCap = -1;
                }
            }
            return swathCap;
        }
    }
}

