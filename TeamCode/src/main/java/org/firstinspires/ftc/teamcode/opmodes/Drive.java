package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
import kotlinx.coroutines.BuildersKt;

@TeleOp
public class Drive extends NextFTCOpMode{
    public Drive(){
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

    @Override
    public void onStartButtonPressed() {
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
    }

    @Override
    public void onUpdate(){
        telemetry.addData("Commands", CommandManager.INSTANCE.snapshot());
        telemetry.update();
    }
}
