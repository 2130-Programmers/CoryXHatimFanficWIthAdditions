// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Button;

import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;


//I made the Dpad a button type here as shown in subsystems they would be a subsystem -cory
public class Dpad extends Button {

    public static POVButton upPOV;
    public static POVButton downPOV;
    public static POVButton leftPOV;
    public static POVButton rightPOV;

    public static boolean upPOVVal;
    public static boolean downPOVVal;
    public static boolean leftPOVVal;
    public static boolean rightPOVVal;

    /**
     * Creates a new Dpad.
     */
    public Dpad(POVButton upPOV, POVButton downPOV, POVButton leftPOV, POVButton rightPOV) {
        /* Tells the referencer that the parameters must be assigned */
        requireNonNullParam(upPOV, "upPUV", "DPad");
        requireNonNullParam(downPOV, "downPOV", "DPad");
        requireNonNullParam(leftPOV, "leftPOV", "DPad");
        requireNonNullParam(rightPOV, "rightPOV", "DPad");

        /* Assigns the local POV buttons to the Class' POV buttons */
        Dpad.upPOV = upPOV;
        Dpad.downPOV = downPOV;
        Dpad.leftPOV = leftPOV;
        Dpad.rightPOV = rightPOV;
    }

    /**
     * A method to ensure the values are being updated at a consitant rate
     */
    public static void runPeriodic() {
        upPOVVal = upPOV.get();
        downPOVVal = downPOV.get();
        leftPOVVal = leftPOV.get();
        rightPOVVal = rightPOV.get();
    }

    /**
     * A similar method to the isDpadActive you had before, but now can be used by the Button superclass
     *
     * @return true if any of the POV buttons are active
     */
    @Override
    public boolean get() {
        return upPOV.get() || downPOV.get() || leftPOV.get() || rightPOV.get();
    }
}
