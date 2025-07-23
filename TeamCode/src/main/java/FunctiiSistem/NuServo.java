package FunctiiSistem;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Hub FTC Test", group = "Iterative Opmode")
public class NuServo extends OpMode {

    // This is the name you give the servo in your robot's configuration file.
    // Make sure it matches the name in the config for the servo on port 0 of your Servo Hub.
    private static final String SERVO_NAME = "servo0";

    private Servo myServo;

    // These are the min and max positions for the servo (0.0 to 1.0)
    private static final double SERVO_MIN_POSITION = 0.0;
    private static final double SERVO_MAX_POSITION = 1.0;
    private static final double SERVO_CENTER_POSITION = 0.5;

    // Amount to change the servo position by each loop
    private static final double SERVO_STEP = 0.01;

    private double currentServoPosition = SERVO_CENTER_POSITION;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        try {
            // Get the servo from the hardware map
            myServo = hardwareMap.get(Servo.class, SERVO_NAME);

            // Set the initial position
            myServo.setPosition(currentServoPosition);

            telemetry.addData("Status", "Initialized");
            telemetry.addData("Servo", "Name: %s", SERVO_NAME);
            telemetry.addData("Servo", "Position: %.2f", currentServoPosition);
        } catch (Exception e) {
            telemetry.addData("Status", "Error during initialization");
            telemetry.addData("Error", "Could not find servo named '%s'. Check your configuration.", SERVO_NAME);
            telemetry.addData("Error Details", e.getMessage());
        }

        telemetry.update();
    }

    @Override
    public void loop() {
        // Use the d-pad to control the servo position
        if (gamepad1.dpad_up) {
            currentServoPosition += SERVO_STEP;
            if (currentServoPosition > SERVO_MAX_POSITION) {
                currentServoPosition = SERVO_MAX_POSITION;
            }
        } else if (gamepad1.dpad_down) {
            currentServoPosition -= SERVO_STEP;
            if (currentServoPosition < SERVO_MIN_POSITION) {
                currentServoPosition = SERVO_MIN_POSITION;
            }
        }

        // Use the 'a' button to center the servo
        if (gamepad1.a) {
            currentServoPosition = SERVO_CENTER_POSITION;
        }

        // Set the new position on the servo
        if (myServo != null) {
            myServo.setPosition(currentServoPosition);
        }

        // Display the current position on the driver station
        telemetry.addData("Servo Position", "%.2f", currentServoPosition);
        telemetry.update();
    }

    @Override
    public void stop() {
        // Nothing to do here for a simple servo opmode, but it's good practice to have the method.
    }
} 