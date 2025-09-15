package org.firstinspires.ftc.teamcode.tests;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;


@TeleOp
public class MotorTest extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotorEx rightFront = null;
    private DcMotorEx leftRear = null;
    private DcMotorEx rightRear = null;

    public void movement() {
        double modifier = 1;//nearBoard ? 0.65 : 1;
        double Lpower = 1*modifier;
        double Rpower = 1; //0.52*modifier;//*modifier;
        boolean reverseStick = true;

        double r = Lpower * Math.hypot((!reverseStick) ? gamepad1.left_stick_x : gamepad1.right_stick_x, (!reverseStick) ? -gamepad1.left_stick_y : -gamepad1.right_stick_y);
        double robotAngle = Math.atan2((!reverseStick) ? -gamepad1.left_stick_y : -gamepad1.right_stick_y, (!reverseStick) ? gamepad1.left_stick_x : gamepad1.right_stick_x) + 3 * Math.PI / 4;
        double rightX = Rpower * ((!reverseStick) ? gamepad1.right_stick_x : gamepad1.left_stick_x) * 1;
        double rightY = Rpower * ((!reverseStick) ? gamepad1.right_stick_y : gamepad1.left_stick_y) * 1;
        double v1 = r * Math.cos(robotAngle) - rightX + rightY;
        double v2 = r * Math.sin(robotAngle) + rightX + rightY;
        double v3 = r * Math.sin(robotAngle) - rightX + rightY;
        double v4 = r * Math.cos(robotAngle) + rightX + rightY;

        leftFront.setPower(v1);
        rightFront.setPower(v2);
        leftRear.setPower(v3);
        rightRear.setPower(v4);
        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setDirection(DcMotorEx.Direction.FORWARD);
        leftRear.setDirection(DcMotorEx.Direction.REVERSE);
        rightRear.setDirection(DcMotorEx.Direction.FORWARD);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotorEx.class, "FL");
        rightFront = hardwareMap.get(DcMotorEx.class, "FR");
        leftRear = hardwareMap.get(DcMotorEx.class, "BL");
        rightRear = hardwareMap.get(DcMotorEx.class, "BR");

        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.circle){
                leftFront.setPower(1);
            }
            else {
                leftFront.setPower(0);
            }
            movement();
        }




    }
}