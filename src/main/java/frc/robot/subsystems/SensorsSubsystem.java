/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SensorsSubsystem extends SubsystemBase {

    private NetworkTable limelightTable;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    public NetworkTableEntry tvert;
    private NetworkTableEntry thor;
    private NetworkTableEntry ts;
    
    
    private static  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public double x;
    public double y;
    public double area;
    public double linearEncoderValue;
    public double h;
    public double v;
    public double offset;

    public AnalogPotentiometer linearEncoder;

    public boolean pipebool = true;

    /**
     * Creates a new SensorsSubsystem.
     */
    public SensorsSubsystem() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

        tx = limelightTable.getEntry("tx");
        ty = limelightTable.getEntry("ty");
        ta = limelightTable.getEntry("ta");
        tvert = limelightTable.getEntry("tvert");
        thor = limelightTable.getEntry("thor");
        ts = limelightTable.getEntry("ts");

        linearEncoder = new AnalogPotentiometer(2);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        h = tvert.getDouble(0.0);
        v = thor.getDouble(0.0);
        offset = ts.getDouble(0.0);

        linearEncoderValue = linearEncoder.get();

    }

    public void layThePipe(){
        if(pipebool == true){
            NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
            pipelineEntry.setNumber(0);
            pipebool = false;
        }else{
            NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
            pipelineEntry.setNumber(1);
            pipebool = true;
        }
    }
    
}
