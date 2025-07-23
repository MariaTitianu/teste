package FunctiiSistem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "IK Sample", group = "Vision")
public class DetectieFinal extends LinearOpMode {
    private Limelight3A limelight;
    private sistemeTeleOp s = new sistemeTeleOp();
    private p p = new p();
    private final double LIMELIGHT_HEIGHT = 31.5;
    private final double LIMELIGHT_OFFSET_X = 11.5;
    private final double LIMELIGHT_OFFSET_Y = 15.5;
    private final double SAMPLE_HEIGHT = 4.0;
    private final double TURELA_MIN_CM = -62.5;
    private final double TURELA_MAX_CM = 10.0;
    private final double ARM_REACH_CM = 14.5;
    private final double ROTIRE_STRAIGHT_POS = p.poz_rotire_normal;
    private final double ROTIRE_DIAGONAL_POS = p.poz_rotire_ciung;
    private final double GHEARAIN_OPEN = p.gheara_in_deschis, GHEARAIN_CLOSED = p.gheara_in_inchis;
    private final double TURELA_MIN_POS = p.turela_detectie, TURELA_MAX_POS = 0.572;
    private final double BRATIN_NEUTRAL = p.bratIN_intermediar;
    private SampleSnapshot lastSampleSnapshot = null;

    private enum IntakeState {MOVE_ABOVE, LOWER, GRAB, DONE}

    private static class SampleSnapshot {
        double tx, ty;

        SampleSnapshot(double tx, double ty) {
            this.tx = tx;
            this.ty = ty;
        }
    }

    private static final double DEFAULT_TX = 0.0;
    private static final double DEFAULT_TY = -10.0;
    private final double EXT_MIN_CM = 13.0;
    private final double EXT_MAX_CM = 43.5;
    private final double EXT_MIN_POS = p.ext_inchis;
    private final double EXT_MAX_POS = p.ext_extins;
    private final double CAMERA_MIN_X = -25; // negativ stanga
    private final double CAMERA_MAX_X = 25; // pozitiv dreapta

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        s.initSistem(hardwareMap);
        s.ext1.setPosition(p.ext_inchis);
        s.ext2.setPosition(p.ext_inchis);
        s.bratIN.setPosition(p.bratIN_static);
        s.turela.setPosition(p.turela_detectie);
        s.rotire.setPosition(ROTIRE_STRAIGHT_POS);
        s.incheietura.setPosition(p.incheiatura_luare);
        s.ghearaIn.setPosition(GHEARAIN_OPEN);
        limelight.pipelineSwitch(0);
        limelight.start();
        waitForStart();
        try {
            boolean asteptare = false;
            while (opModeIsActive()) {
                LLResult result = limelight.getLatestResult();
                double tx = DEFAULT_TX;
                double ty = DEFAULT_TY;
                if (result != null) {
                    tx = result.getTx();
                    ty = result.getTy();
                }
                boolean sampleDetected = (tx != DEFAULT_TX || ty != DEFAULT_TY);
                if (!sampleDetected) {
                    telemetry.addLine("Nu sample");
                    telemetry.update();
                    asteptare = false;
                    sleep(100);
                    continue;
                }
                if (asteptare) {
                    telemetry.addLine("stop");
                    telemetry.update();
                    sleep(100);
                    continue;
                }
                double[] snapshotTargetPos = calculateTargetPosition(tx, ty);
                double snapshotTargetX = snapshotTargetPos[0];
                double snapshotTargetY = snapshotTargetPos[1];
                double snapshotTargetZ = snapshotTargetPos[2];
                double forwardDist = Math.hypot(snapshotTargetX, snapshotTargetY);
                double armReach = Math.min(forwardDist, ARM_REACH_CM);
                double requiredExtension = Math.max(EXT_MIN_CM, Math.min(EXT_MAX_CM, forwardDist - armReach));

                double extPercent = (requiredExtension - EXT_MIN_CM) / (EXT_MAX_CM - EXT_MIN_CM);
                extPercent = Math.max(0.0, Math.min(1.0, extPercent));
                double extServoPos = EXT_MAX_POS + (EXT_MIN_POS - EXT_MAX_POS) * extPercent;
                // Use ty for turret calculation because Limelight is rotated 90 degrees CCW
                double[] ik = calculateInverseKinematics(ty, snapshotTargetY, snapshotTargetZ);
                double ikBratIN = ik[0];
                double ikTurela = ik[1];
                double ikRotire = ik[2];
                s.bratIN.setPosition(p.bratIN_static);
                s.turela.setPosition(ikTurela);
                s.rotire.setPosition(ikRotire);
                s.ghearaIn.setPosition(GHEARAIN_OPEN);
                s.ext1.setPosition(extServoPos);
                s.ext2.setPosition(extServoPos);
                telemetry.addData("Target X", snapshotTargetX);
                telemetry.addData("Target Y", snapshotTargetY);
                telemetry.addData("Target Z", snapshotTargetZ);
                telemetry.addData("BratIN", p.bratIN_static);
                telemetry.addData("Turela", ikTurela);
                telemetry.addData("IK Rotire", ikRotire);
                telemetry.addData("Ext Servo Pos", extServoPos);
                telemetry.update();
                sleep(350);
                s.bratIN.setPosition(ikBratIN);
                s.turela.setPosition(ikTurela);
                s.rotire.setPosition(ikRotire);
                s.ghearaIn.setPosition(GHEARAIN_OPEN);
                s.ext1.setPosition(extServoPos);
                s.ext2.setPosition(extServoPos);
                telemetry.addData("IK BratIN", ikBratIN);
                telemetry.addData("Ext Servo Pos", extServoPos);
                telemetry.update();
                sleep(700);
                s.ghearaIn.setPosition(GHEARAIN_CLOSED);
                s.ext1.setPosition(extServoPos);
                s.ext2.setPosition(extServoPos);
                telemetry.addData("Ext Servo Pos", extServoPos);
                telemetry.update();
                sleep(1000);
                s.bratIN.setPosition(p.bratIN_static);
                telemetry.addLine("Sample luat!");
                telemetry.update();
                asteptare = true;
                sleep(500);
            }
        } catch (Exception e) {
            telemetry.addLine("Exception: " + e.getMessage());
            telemetry.addData("ARM_REACH_CM", ARM_REACH_CM);
            telemetry.addData("lastSampleSnapshot", lastSampleSnapshot != null ? (lastSampleSnapshot.tx + "," + lastSampleSnapshot.ty) : "null");
            telemetry.update();
            sleep(5000);
        }
    }

    private double[] calculateTargetPosition(double tx, double ty) {
        double swappedTx = ty;
        double swappedTy = tx;
        double cameraHeight = LIMELIGHT_HEIGHT;
        double targetHeight = SAMPLE_HEIGHT;
        double cameraAngle = 42.5;
        double totalAngleY = cameraAngle + swappedTy;
        double totalAngleYRad = Math.toRadians(totalAngleY);

        if (Math.abs(totalAngleYRad) < 0.01)
            totalAngleYRad = 0.01 * (totalAngleYRad == 0 ? 1 : Math.signum(totalAngleYRad));

        double distanceToTarget = (cameraHeight - targetHeight) / Math.tan(totalAngleYRad);
        if (Double.isNaN(distanceToTarget) || Double.isInfinite(distanceToTarget) || distanceToTarget < 0) {
            distanceToTarget = 0;
        }

        double horizontalAngleRad = Math.toRadians(swappedTx);
        double cameraX = distanceToTarget * Math.tan(horizontalAngleRad);
        double cameraY = distanceToTarget;

        double robotX = cameraX + LIMELIGHT_OFFSET_X;
        double robotY = cameraY + LIMELIGHT_OFFSET_Y;

        double targetX = robotX;
        double targetY = robotY;
        double targetZ = 0;

        telemetry.addData("tx", tx);
        telemetry.addData("ty", ty);
        telemetry.addData("swappedTx", swappedTx);
        telemetry.addData("swappedTy", swappedTy);
        telemetry.addData("distanceToTarget", distanceToTarget);
        telemetry.addData("targetX", targetX);
        telemetry.addData("targetY", targetY);
        return new double[]{targetX, targetY, targetZ};
    }

    private double[] calculateInverseKinematics(double tx, double targetY, double targetZ) {
        double clampedTx = Math.max(CAMERA_MIN_X, Math.min(CAMERA_MAX_X, tx));
        double t = (clampedTx - CAMERA_MIN_X) / (CAMERA_MAX_X - CAMERA_MIN_X);
        double turelaPos = TURELA_MAX_POS + (1 - t) * (TURELA_MIN_POS - TURELA_MAX_POS);
        turelaPos = Math.max(TURELA_MIN_POS, Math.min(TURELA_MAX_POS, turelaPos));
        double safeX = Math.max(TURELA_MIN_CM, Math.min(TURELA_MAX_CM, tx));
        double safeY = Math.max(0, Math.min(EXT_MAX_CM, targetY));
        double distance = Math.hypot(safeX, safeY);
        double bratINPos = BRATIN_NEUTRAL + (distance / ARM_REACH_CM) * 0.3;
        bratINPos = Math.max(p.bratIN_static, Math.min(p.bratIN_luare, bratINPos));
        double angleToSample = Math.toDegrees(Math.atan2(-safeX, safeY));
        double rotirePos;
        if (angleToSample <= -67.5) {
            rotirePos = p.poz_rotire_ciung_stanga;
        } else if (angleToSample > -67.5 && angleToSample <= -22.5) {
            rotirePos = interpolate(p.poz_rotire_ciung_stanga, ROTIRE_DIAGONAL_POS, (angleToSample + 67.5) / 45.0);
        } else if (angleToSample > -22.5 && angleToSample <= 22.5) {
            rotirePos = interpolate(ROTIRE_DIAGONAL_POS, p.poz_rotire_ciung, (angleToSample + 22.5) / 45.0);
        } else if (angleToSample > 22.5 && angleToSample <= 67.5) {
            rotirePos = interpolate(p.poz_rotire_ciung, ROTIRE_DIAGONAL_POS, (angleToSample - 22.5) / 45.0);
        } else if (angleToSample > 67.5) {
            rotirePos = p.poz_rotire_ciung_dreapta;
        } else {
            rotirePos = p.poz_rotire_ciung;
        }
        return new double[]{bratINPos, turelaPos, rotirePos};
    }

    private double interpolate(double start, double end, double t) {
        return start + (end - start) * t;
    }
}