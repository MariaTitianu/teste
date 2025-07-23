package FunctiiSistem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

/**
 * Vision calibration OpMode for Limelight 3A.
 * - Uses pipeline 0 (blue object detection)
 * - Target height: 0 cm (ground level)
 * - Uses detected angle from pipeline (0-180 degrees)
 * - Configurable X offset
 */
@TeleOp(name = "Detectie Calibrare", group = "Vision")
public class DetectieCalibrare extends LinearOpMode {
    private Limelight3A limelight;

    // --- Calibration constants (as set by user) ---
    private final double LIMELIGHT_HEIGHT_FROM_GROUND = 23.5; // cm
    private final double LIMELIGHT_MOUNT_ANGLE = 0; // deg
    private double LIMELIGHT_OFFSET_X = 12.0; // cm, configurable X offset (change as needed)
    private final double LIMELIGHT_OFFSET_Y = 23.5; // cm, lateral offset
    private final double TARGET_HEIGHT_FROM_GROUND = 0; // cm (as requested)
    private final double TY_HORIZONTAL_OFFSET = 11.0; // deg, camera-specific
    // --- End of constants ---

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(50);
        limelight.pipelineSwitch(0); // Use pipeline 0 (blue object detection)
        limelight.start();

        waitForStart();

        while (opModeIsActive()) {
            LLResult limelightResult = limelight.getLatestResult();

            if (limelightResult != null) {
                // Get detected angles from Limelight pipeline 0
                double detectedAngleVertical = limelightResult.getTx(); // Vertical angle to target
                double detectedAngleHorizontal = limelightResult.getTy(); // Horizontal angle to target
                
                // Get additional data from pipeline 0
                double[] llpython = limelightResult.getPythonOutput();
                double detectedObjectAngle = 0.0; // Angle from pipeline 0 (0-180 degrees)
                int numSamples = 0;
                boolean isSeparated = false;
                double separationConfidence = 0.0;
                
                if (llpython != null && llpython.length >= 9) {
                    detectedObjectAngle = llpython[8]; // Angle from pipeline (0-180 degrees)
                    numSamples = (int)llpython[1];
                    isSeparated = llpython[2] == 1;
                    separationConfidence = llpython[2]; // This will be 0 or 1, but we can use it
                }

                // --- Step 1: Correct the horizontal angle for offset and inversion ---
                double angleAfterOffset = detectedAngleHorizontal - TY_HORIZONTAL_OFFSET;
                double horizontalAngle = -angleAfterOffset;

                // --- Step 2: Calculate distance to target based on vertical angle ---
                double totalVerticalAngle = LIMELIGHT_MOUNT_ANGLE + detectedAngleVertical;
                double heightDifference = LIMELIGHT_HEIGHT_FROM_GROUND - TARGET_HEIGHT_FROM_GROUND;
                double distanceToTarget = heightDifference / Math.tan(Math.toRadians(totalVerticalAngle));

                // --- Step 3: Calculate target's (X, Y) coordinates relative to the CAMERA ---
                double x_cam = distanceToTarget;
                double y_cam = distanceToTarget * Math.tan(Math.toRadians(horizontalAngle));

                // --- Step 4: Transform target coordinates to be relative to the ARM PIVOT ---
                double x_robot = x_cam - LIMELIGHT_OFFSET_X;
                double y_robot = y_cam - LIMELIGHT_OFFSET_Y;

                // --- Step 5: Calculate Required Arm Angle and Extension Length (IK) ---
                double requiredExtensionCm = Math.sqrt(x_robot * x_robot + y_robot * y_robot);
                double requiredArmAngleDeg = Math.toDegrees(Math.atan2(y_robot, x_robot));

                // --- Telemetry for calibration and debugging ---
                telemetry.addLine("--- VISION CALIBRATION ---");
                telemetry.addData("Detected Angle Vertical (TX)", "%.2f deg", detectedAngleVertical);
                telemetry.addData("Detected Angle Horizontal (TY)", "%.2f deg", detectedAngleHorizontal);
                telemetry.addData("Pipeline 0 Object Angle", "%.2f deg", detectedObjectAngle);
                telemetry.addData("Number of Samples", numSamples);
                telemetry.addData("Objects Separated", isSeparated ? "YES" : "NO");
                telemetry.addLine();
                telemetry.addData("Corrected Horizontal Angle", "%.2f deg", horizontalAngle);
                telemetry.addData("Calculated Distance to Target", "%.2f cm", distanceToTarget);
                telemetry.addLine();
                telemetry.addData("Target X relative to PIVOT", "%.2f cm", x_robot);
                telemetry.addData("Target Y relative to PIVOT", "%.2f cm", y_robot);
                telemetry.addLine();
                telemetry.addLine("--- FINAL RESULTS ---");
                telemetry.addData("Required Extension", "%.2f cm", requiredExtensionCm);
                telemetry.addData("Required Arm Angle", "%.2f deg", requiredArmAngleDeg);
                telemetry.addLine();
                telemetry.addData("Configurable X Offset", "%.2f cm", LIMELIGHT_OFFSET_X);
            } else {
                telemetry.addLine("--- NO TARGET DETECTED ---");
            }

            telemetry.update();
        }
    }
}