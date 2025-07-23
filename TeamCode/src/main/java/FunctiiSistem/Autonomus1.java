package FunctiiSistem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autonomus1", group = "Competition")
public class Autonomus1 extends OpMode {

    sistemeAutonomAmerica s = new sistemeAutonomAmerica(this);
    public PidControllerAdevarat pidSlider = new PidControllerAdevarat(0, 0, 0);
    public Follower follower;
    public boolean ceva, altceva, ceva2 = false, stop = false;
    double pidResult, pidResultB, pidResultSasiu, poz_dr, poz_st;
    public Timer pathTimer, opmodeTimer;
    public int pathState;

    // Starting pose of the robot
    public final Pose startPose = new Pose(8.500, 65.550, Math.toRadians(0));

    // Combined path chain
    public PathChain fullPath;
    public Limelight3A limelight;
    public final double LIMELIGHT_HEIGHT = 16;     // Height of Limelight from ground in cm
    public final double LIMELIGHT_ANGLE = 25;      // Angle of Limelight from horizontal in degrees

    // Extension Configuration
    public final double MIN_EXTENSION = p.ext_inchis;
    public final double MAX_EXTENSION = 0.45;
    public final double MIN_DISTANCE = 9;         // Minimum safe operating distance in cm
    public final double MAX_DISTANCE = 35.0;      // Maximum reach distance in cm
    public final double TARGET_OFFSET = 11;       // Distance to maintain behind the object in cm

    // Arm Configuration
    public final double INITIAL_LEFT_POS = 0.593;
    public final double INITIAL_RIGHT_POS = 0.625;
    public final double MAX_PIVOT_ADJUSTMENT = 0.2; // Fine-tuned for ty-based movement
    public final double TX_OFFSET = -16.50;
    public final double TY_CENTER = 11;          // Center point for ty

    // Movement Constants
    public final double TY_SENSITIVITY_LEFT = 0.7;  // Higher sensitivity for left side
    public final double TY_SENSITIVITY_RIGHT = 0.7; // Normal sensitivity for right side
    public final double DISTANCE_SCALE = 1;

    public PathChain firstPath, secondPath, thirdPath, forthPath, PusPrimul, Human1, Pus2, Human2, Pus3, Human3, Pus4, Human4, PusTerminare, HumanTerminare;

    public void buildPaths() {
        // First path chain - just Line 1
        firstPath = follower.pathBuilder()
                .addPath(
                    new BezierLine(
                        new Point(8.500, 65.550, Point.CARTESIAN),
                        new Point(35.250, 65.550, Point.CARTESIAN)
                    )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(8)
                .build();

        // Second path chain - Lines 2-6
        secondPath = follower.pathBuilder()
                // Line 2
                .addPath(
                        new BezierCurve(
                                new Point(35.250, 65.550, Point.CARTESIAN),
                                new Point(14.400, 32.566, Point.CARTESIAN),
                                new Point(85.957, 14.178, Point.CARTESIAN),
                                new Point(51.175, 50.068, Point.CARTESIAN),
                                new Point(49.182, 14.622, Point.CARTESIAN),
                                new Point(77.412, 23.926, Point.CARTESIAN),
                                new Point(27.591, 21.268, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(6)
                // Line 3
                .addPath(
                        new BezierCurve(
                                new Point(24.591, 21.268, Point.CARTESIAN),
                                new Point(86.622, 26.585, Point.CARTESIAN),
                                new Point(26.142, 12.406, Point.CARTESIAN),
                                new Point(85.292, 11.963, Point.CARTESIAN),
                                new Point(21.000, 11.742, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-28.5))
                .setZeroPowerAccelerationMultiplier(8)  // 10 second timeout for the entire path
                .build();
//        thirdPath = follower.pathBuilder()
//                //linia 5
//                .addPath(
//                        new BezierCurve(
//                                new Point(56.271, 11.520, Point.CARTESIAN),
//                                new Point(32.123, 12.849, Point.CARTESIAN),
//                                new Point(23.926, 13.071, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-27.5))
//                .setZeroPowerAccelerationMultiplier(8)
//                .build();

        forthPath = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(21.100, 11.742, Point.CARTESIAN),
                                new Point(25.255, 25.698, Point.CARTESIAN),
                                new Point(8.500, 26.363, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
        PusPrimul = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(8.500, 26.000, Point.CARTESIAN),
                                new Point(12.406, 63.582, Point.CARTESIAN),
                                new Point(40.062, 63.234, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(6))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
        Human1 = follower.pathBuilder()
                .addPath( new BezierCurve(
                        new Point(37.062, 64.234, Point.CARTESIAN),
                        new Point(28.135, 30.794, Point.CARTESIAN),
                        new Point(11.748, 31.363, Point.CARTESIAN)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(6), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
        Pus2 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(13.748, 31.363, Point.CARTESIAN),
                                new Point(12.406, 63.582, Point.CARTESIAN),
                                new Point(38.062, 62.234, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(6))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
        Human2 = follower.pathBuilder()
                .addPath( new BezierCurve(
                        new Point(38.062, 62.234, Point.CARTESIAN),
                        new Point(28.135, 30.794, Point.CARTESIAN),
                        new Point(11.748, 31.363, Point.CARTESIAN)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(6), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
        Pus3 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(13.748, 31.363, Point.CARTESIAN),
                                new Point(12.406, 63.582, Point.CARTESIAN),
                                new Point(39.062, 60.234, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(6))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
        Human3 = follower.pathBuilder()
                .addPath( new BezierCurve(
                        new Point(39.062, 60.234, Point.CARTESIAN),
                        new Point(28.135, 30.794, Point.CARTESIAN),
                        new Point(11.748, 31.363, Point.CARTESIAN)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(6), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
        Pus4 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(13.748, 31.363, Point.CARTESIAN),
                                new Point(12.406, 63.582, Point.CARTESIAN),
                                new Point(39.062, 58.234, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(6))
                .setZeroPowerAccelerationMultiplier(6)
                .build();
        Human4 = follower.pathBuilder()
                .addPath( new BezierCurve(
                        new Point(39.062, 58.234, Point.CARTESIAN),
                        new Point(28.135, 30.794, Point.CARTESIAN),
                        new Point(11.748, 31.363, Point.CARTESIAN)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)

                .build();
        PusTerminare = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(11.748, 26.363, Point.CARTESIAN),
                                new Point(12.406, 63.582, Point.CARTESIAN),
                                new Point(40.062, 57.234, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(8)
                .build();
        HumanTerminare = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Point(36.062, 56.205, Point.CARTESIAN),
                        new Point(26.585, 52.283, Point.CARTESIAN),
                        new Point(10.701, 29.363, Point.CARTESIAN)
                ))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(14)
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                new Thread(()->{
                    sculare();
                    // Start sculare() immediately with the first path
                }).start();
                follower.followPath(firstPath, 0.9,/* holdEnd */ true );
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    s.kdf(25);
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    new Thread(()->{
                        retragereSliderSteroizi_prima();
                        s.kdf(4500);
                        mid();
                    }).start();
                    follower.followPath(secondPath, 1,/* holdEnd */ true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()){
                    s.kdf(100);
                    new Thread(()->{
                        luat();
                    }).start();
                    s.kdf(100);
                    follower.followPath(forthPath, 1,/* holdEnd */ true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    s.ghearaIn.setPosition(p.gheara_in_deschis);
                    s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                    s.kdf(50);
                    new Thread(()->{
                        punere_gard_detectie();
                    }).start();
                    follower.followPath(PusPrimul, 1,/* holdEnd */ true);
                    s.incheietura.setPosition(p.incheiatura_luare);
//                    s.brat_dr.setPosition(p.brat_dr_init);
//                    s.brat_st.setPosition(p.brat_st_init);
                    s.rotire.setPosition(p.poz_rotire_ciung);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy()){
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    s.kdf(2000);
                    recunoastere();
                    s.kdf(400);
                    s.brat_st.setPosition(s.brat_st.getPosition()-0.2); // 468
                    s.brat_dr.setPosition(s.brat_dr.getPosition() -0.2); // 496
                    s.kdf(200);
                    s.ghearaIn.setPosition(p.gheara_in_inchis);
                    s.kdf(500);
                    new Thread(()->{
                        s.ext1.setPosition(p.ext_inchis);
                        s.ext2.setPosition(p.ext_inchis);
                        s.kdf(200);
                        s.incheietura.setPosition(p.incheietura_human);
                        s.planulB.setPosition(Pozitii.planulB_luare_gard);
                        s.brat_st.setPosition(0.587); // 468
                        s.brat_dr.setPosition(0.543);
                        s.rotire.setPosition(0.43);
                        s.kdf(700);
                        poz_dr = s.brat_dr.getPosition();
                        poz_st = s.brat_st.getPosition();
                        while (s.brat_dr.getPosition() > 0.214) { //24
                            poz_dr = poz_dr - 0.012;
                            poz_st = poz_st + 0.012;
                            s.brat_dr.setPosition(poz_dr);
                            s.brat_st.setPosition(poz_st);
                        }
                    }).start();
                    new Thread(()->{
                        s.kdf(100);
                        retragereSliderSteroizi();
                    }).start();

                    follower.followPath(Human1, 1,/* holdEnd */ true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                        punere_gard();
                    }).start();
                    follower.followPath(Pus2,1,/* holdEnd */ true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()){
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    new Thread(()->{
                        s.kdf(100);
                        retragereSliderSteroizi();
                    }).start();
                    follower.followPath(Human2, 1,/* holdEnd */ true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                        punere_gard();
                    }).start();
                    follower.followPath(Pus3, 1,/* holdEnd */ true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()){
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    new Thread(()->{
                        s.kdf(100);
                        retragereSliderSteroizi();
                    }).start();
                    follower.followPath(Human3, 1,/* holdEnd */ true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                        punere_gard();
                    }).start();
                    follower.followPath(Pus4, 1,/* holdEnd */ true);
                    setPathState(11);
                }
                break;
            case 11:
                if (!follower.isBusy()){
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    new Thread(()->{
                        s.kdf(100);
                        retragereSliderSteroizi();
                    }).start();
                    follower.followPath(Human4, 1,/* holdEnd */ true);
                    setPathState(12);
                }
                break;
            case 12:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                        punere_gard();
                    }).start();
                    follower.followPath(PusTerminare, 1,/* holdEnd */ true);
                    setPathState(13);
                }
                break;
            case 13:
                if (!follower.isBusy()){
                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    new Thread(()->{
                        s.kdf(100);
                        retragereSliderSteroizi();
                    }).start();
                    follower.followPath(HumanTerminare, 1,/* holdEnd */ true);
                    setPathState(14);
                }
                break;
            case 14:
                if (!follower.isBusy()) {
                    setPathState(-1); // End of path sequence
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
//        telemetry.addData("path state", pathState);
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading", follower.getPose().getHeading());
//        telemetry.update();
    }

//    @Override
//    public void runOpMode() throws InterruptedException {
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        // Optional: reduce telemetry latency
//        telemetry.setMsTransmissionInterval(11);
//        // Set Limelight pipeline index (adjust as needed)
//        limelight.pipelineSwitch(5);
//        limelight.start();
//        s.init(hardwareMap);
//        telemetry.addLine("waiting for start:");
//        telemetry.update();
//        pid_slider.start();
//
//
//        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
//
//
//
//
////
//        while (!isStopRequested() && !isStarted()) {
//            try {
//                s.pozitii_init_bara();
//                s.brat_dr.setPosition(0.292);
//                s.brat_st.setPosition(0.818);
//                telemetry.addData("sigma:", " sigma");
//                telemetry.update();
//                pathTimer = new Timer();
//                opmodeTimer = new Timer();
//                opmodeTimer.resetTimer();
//                follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
//                follower.setStartingPose(startPose);
//                buildPaths();
//            } catch (Exception E) {
//                //varrez = "Stanga";
//                telemetry.addData("Webcam error:", "please restart");
//                telemetry.update();
//            }
//            telemetry.update();
//        }
//        opmodeTimer.resetTimer();
//        sculare();
//        setPathState(0);
//
//    }

    @Override
    public void init() {
        s.init(hardwareMap);
        s.pozitii_init_bara();
        s.brat_dr.setPosition(0.292);
        s.brat_st.setPosition(0.818);
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        pid_slider.start();
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // Optional: reduce telemetry latency
        telemetry.setMsTransmissionInterval(11);
        // Set Limelight pipeline index (adjust as needed)
        limelight.pipelineSwitch(5);
        limelight.start();
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
//        new Thread(()->{
//
//        }).start();
//        s.kdf(500);
//        sculare();
//        s.kdf(2000);
//        retragereSliderSteroizi_prima();

    }

    @Override
    public void stop() {
        stop = true;
    }
    public synchronized void punere_gard(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(50);
        ceva2 = true;
        s.ansamblul_leleseana(-1060, 5000, 0, true);
        ceva2 = false;
        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
        s.planulB.setPosition(Pozitii.planulB_deschis);
        s.incheieturaout.setPosition(Pozitii.incheietura_bara);


        // s.kdf(200);
    }
    public synchronized void punere_gard_detectie(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.brat_st.setPosition(p.brat_dr_extindere);
//        s.brat_dr.setPosition(p.brat_st_extindere);
        s.kdf(50);
        ceva2 = true;
        s.ansamblul_leleseana(-1060, 5000, 0, true);
        ceva2 = false;
        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
        s.planulB.setPosition(Pozitii.planulB_deschis);
        s.incheieturaout.setPosition(Pozitii.incheietura_bara);


        // s.kdf(200);
    }
    public synchronized void punere_gard_simpla(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(125);
        ceva2 = true;
        s.ansamblul_leleseana(-1055, 5000, 0, true);
        ceva2 = false;
        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
        s.planulB.setPosition(Pozitii.planulB_deschis);
        s.kdf(50);
        s.incheieturaout.setPosition(Pozitii.incheietura_bara);
    }
    public synchronized void mid(){
        s.rotire.setPosition(0.815);
        s.ext1.setPosition(0.37);
        s.ext2.setPosition(0.37);
        s.brat_dr.setPosition(p.brat_dr_extindere);
        s.brat_st.setPosition(p.brat_st_extindere);
        s.incheietura.setPosition(p.incheiatura_luare);
        //s.ghearaO.setPosition(Pozitii.gheara_deschis);
    }
    public synchronized void luat(){
        s.brat_st.setPosition(p.brat_st_luare); // 468
        s.brat_dr.setPosition(p.brat_dr_luare); // 496
        s.kdf(50);
        s.ghearaIn.setPosition(p.gheara_in_inchis);
        //retragere_ext();
        new Thread(()->{
            s.kdf(150);
            s.ext1.setPosition(p.ext_inchis);
            s.ext2.setPosition(p.ext_inchis);
            s.incheietura.setPosition(p.incheietura_human);
            s.planulB.setPosition(Pozitii.planulB_luare_gard);
            s.brat_st.setPosition(0.587); // 468
            s.brat_dr.setPosition(0.543);
            s.rotire.setPosition(0.43);
            s.kdf(100);
            poz_dr = s.brat_dr.getPosition();
            poz_st = s.brat_st.getPosition();
            while (s.brat_dr.getPosition() > 0.214) { //24
                poz_dr = poz_dr - 0.012;
                poz_st = poz_st + 0.012;
                s.brat_dr.setPosition(poz_dr);
                s.brat_st.setPosition(poz_st);
            }
        }).start();


    }

    public synchronized void recunoastere(){
        LLResult limelightResult = limelight.getLatestResult();
        while(limelightResult == null){
            limelightResult = limelight.getLatestResult();
        }

        if (limelightResult.getTx() != 0 || limelightResult.getTy() != 0) {
            // Calculate all positions first
            double ty = -limelightResult.getTy() + 12;
            double tx = limelightResult.getTx() - TX_OFFSET;

            // Calculate distance using tx for extension
            double targetAngleHorizontal = tx;
            double targetDistance = LIMELIGHT_HEIGHT / Math.tan(Math.toRadians(LIMELIGHT_ANGLE + targetAngleHorizontal));
            targetDistance = Math.max(MIN_DISTANCE, Math.min(targetDistance * DISTANCE_SCALE, MAX_DISTANCE));

            // Calculate extension based on distance
            double extensionPercentage = (targetDistance - MIN_DISTANCE) / (MAX_DISTANCE - MIN_DISTANCE);
            double targetExtension = MIN_EXTENSION + (extensionPercentage * (MAX_EXTENSION - MIN_EXTENSION));
            targetExtension = clamp(targetExtension, MIN_EXTENSION, MAX_EXTENSION);

            // Calculate arm positions based on ty with asymmetric sensitivity
            double tySensitivity = (ty < TY_CENTER) ? TY_SENSITIVITY_LEFT : TY_SENSITIVITY_RIGHT;
            double tyScaled = ty * tySensitivity;

            // Additional boost for left side movement
//                    if (ty < TY_CENTER) {
//                        tyScaled *= 1.45; // 30% extra movement for left side
//                    }
//                    if (ty > TY_CENTER) {
//                        tyScaled *= 0.65; // 30% extra movement for left side
//                    }

            double armAdjustment = (tyScaled / 15.0) * MAX_PIVOT_ADJUSTMENT;

            // Calculate final arm positions with improved precision
            double newLeftPos = INITIAL_LEFT_POS - armAdjustment;
            double newRightPos = INITIAL_RIGHT_POS + armAdjustment;

            // Apply bounds for arm movement
            newLeftPos = clamp(newLeftPos, 0.458, 0.72);
            newRightPos = clamp(newRightPos, 0.458, 0.72);

            // Execute movements in sequence
            // 1. Set extension position first
            if (ty > 11){
                s.ext1.setPosition(targetExtension - 0.05);
                s.ext2.setPosition(targetExtension - 0.05);
            }
            else {
                s.ext1.setPosition(targetExtension - 0.02);
                s.ext2.setPosition(targetExtension - 0.02);
            }

            s.brat_st.setPosition(INITIAL_LEFT_POS);
            s.brat_dr.setPosition(INITIAL_RIGHT_POS);
            s.kdf(300); // Wait for extension to reach position

            // 2. Set arm positions after extension is in place

            s.brat_st.setPosition(newLeftPos);
            s.brat_dr.setPosition(newRightPos);



        }
    }
    public double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
    public synchronized void retragereSlider(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(50);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);

        ceva2 = true;


        while (!s.taci_slider.isPressed()) {
            s.sliderD.setVelocity(5000);
            s.sliderS.setVelocity(5000);

        }
        s.sliderD.setVelocity(0);
        s.sliderS.setVelocity(0);

        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
        new Thread(()->{
            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


            ceva2 = false;
            ceva = true;
        }).start();


    }
    public synchronized void retragereSliderSteroizi(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(50);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
        //s.bratOut2.setPosition(p.out_gard_tele);
//                    s.incheietura_out.setPosition(p.incheietura_gard_tele);
        //s.retragere_slider();
        ceva2 = true;
        while (!s.taci_slider.isPressed()) {
            s.sliderD.setVelocity(5000);
            s.sliderS.setVelocity(5000);

        }
        s.sliderD.setVelocity(0);
        s.sliderS.setVelocity(0);


        new Thread(()->{
            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


            ceva2 = false;
            ceva = true;

        }).start();
        s.kdf(200);
        s.planulB.setPosition(Pozitii.planulB_luare_gard);
        s.ghearaOut.setPosition(Pozitii.gheara_deschis);


    }
    public synchronized void retragereSliderSteroizi_prima(){
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(50);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
        //s.bratOut2.setPosition(p.out_gard_tele);
//                    s.incheietura_out.setPosition(p.incheietura_gard_tele);
        //s.retragere_slider();
        ceva2 = true;
        while (!s.taci_slider.isPressed()) {
            s.sliderD.setVelocity(5000);
            s.sliderS.setVelocity(5000);

        }
        s.sliderD.setVelocity(0);
        s.sliderS.setVelocity(0);


        new Thread(()->{
            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


            ceva2 = false;
            ceva = true;
        }).start();
        s.kdf(50);
        s.planulB.setPosition(Pozitii.planulB_luare_gard);
        s.ghearaOut.setPosition(Pozitii.gheara_deschis);


    }
    public synchronized void sculare(){
        //s.incheietura.setPosition(p.incheiatura_luare);
        ceva2 = true;
        s.punere_lelese(-1040, 5000, 5, true);
        ceva2 = false;
    }

    public final Thread pid_slider = new Thread(new Runnable() {
        @Override
        public void run() {
            pidSlider.enable();
            while(!stop){
                pidSlider.setPID(Config.pslider, Config.islider, Config.dslider);
                if(ceva2){
                    ceva = true;
                }
                else{
                    if(ceva){
                        ceva = false;
                        pidSlider.setSetpoint(s.sliderD.getCurrentPosition());
                    }
                    if (s.taci_slider.isPressed()){
                        s.sliderD.setPower(0);
                        s.sliderS.setPower(0);
                    }
                    else {
                        pidResult = pidSlider.performPID(s.sliderD.getCurrentPosition());
                        s.sliderD.setPower(pidResult);
                        s.sliderS.setPower(pidResult);
                    }
                }
            }
        }
    });

}
