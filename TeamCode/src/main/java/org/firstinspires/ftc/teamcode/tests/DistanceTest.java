package org.firstinspires.ftc.teamcode.tests;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
@TeleOp
public class DistanceTest extends NextFTCOpMode {
    public DistanceTest(){
        addComponents(
                new SubsystemComponent(),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }
    // change the names and directions to suit your robot
    private MotorEx frontLeftMotor;
    private MotorEx frontRightMotor;
    private MotorEx backLeftMotor;
    private MotorEx backRightMotor;
    private Limelight3A limelight;

    @Override
    public void onStartButtonPressed() {
        limelight.pipelineSwitch(9);
        limelight.start();
        Command driverControlled = new MecanumDriverControlled(
                frontRightMotor,
                frontLeftMotor,
                backRightMotor,
                backLeftMotor ,
                Gamepads.gamepad1().rightStickY(),
                Gamepads.gamepad1().rightStickX(),
                Gamepads.gamepad1().leftStickX()
        );
        driverControlled.schedule();

    }
    @Override
    public void onInit(){
        frontLeftMotor = new MotorEx("FL").reversed();
        frontRightMotor = new MotorEx("FR");
        backLeftMotor = new MotorEx("BL").reversed();
        backRightMotor = new MotorEx("BR");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }
    @Override
    public void onUpdate() {

        LLResult result = limelight.getLatestResult();

        if(result.isValid()){
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                Pose botPose = new Pose(fr.getRobotPoseFieldSpace().getPosition().x,fr.getRobotPoseFieldSpace().getPosition().y,fr.getRobotPoseFieldSpace().getOrientation().getYaw(AngleUnit.RADIANS));

                telemetry.addData("Pipeline", result.getPipelineIndex());
                telemetry.addData("Fiducial", "ID: %d, Family: %s", fr.getFiducialId(), fr.getFamily());
                telemetry.addData("x", fr.getRobotPoseFieldSpace().getPosition().x);
                telemetry.addData("y", fr.getRobotPoseFieldSpace().getPosition().y);
                telemetry.addData("z", fr.getRobotPoseFieldSpace().getPosition().z);
                telemetry.addData("yaw", fr.getRobotPoseFieldSpace().getOrientation().getYaw());
                telemetry.addData("pitch", fr.getRobotPoseFieldSpace().getOrientation().getPitch());
                telemetry.addData("roll", fr.getRobotPoseFieldSpace().getOrientation().getRoll());

                telemetry.addData("PedroX", follower.getPose().getX());
                telemetry.addData("PedroY", follower.getPose().getY());
                telemetry.addData("Pedro Heading", follower.getPose().getHeading());
                telemetry.addData("PedroPose", follower.getPose());
                telemetry.addData("LLPose", result.getBotpose());

            }


        }
        follower.update();
        telemetry.update();
    }
}
