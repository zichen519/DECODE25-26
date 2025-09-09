package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp
public class DecodeTest extends LinearOpMode {
    Limelight3A limelight;
    int motifId;
    @Override
    public void runOpMode() throws InterruptedException {

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        waitForStart();
        limelight.start();
        while(!isStopRequested() && opModeIsActive()){
            LLResult result = limelight.getLatestResult();
            if(result.isValid()){
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                    motifId = fr.getFiducialId();
                }

            }
            if(motifId == 21){
                telemetry.addLine("GREEN PURPLE PURPLE");
            } else if(motifId == 22){
                telemetry.addLine("PURPLE GREEN PURPLE");
            } else if(motifId == 23){
                telemetry.addLine("PURPLE PURPLE GREEN");
            } else {
                telemetry.addLine("No valid ID");
            }
            telemetry.update();
        }
    }
}
