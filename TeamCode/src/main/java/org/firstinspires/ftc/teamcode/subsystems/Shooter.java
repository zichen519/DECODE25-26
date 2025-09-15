package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter(){};

    private MotorEx shooterMotor = new MotorEx("shooter");

    public Command runForward = new SetPower(shooterMotor,1);
    public Command runBackward = new SetPower(shooterMotor,-1);
}
