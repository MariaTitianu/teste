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

@Autonomous(name = "Autosugus_Albastru_Bara", group = "Competition")
public class Autonomus_Albastru_Bara extends OpMode {

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
         // Distance to maintain behind the object in cm

    // Arm Configuration
            // Center point for ty

    // Movement Constants

    public PathChain firstPath, secondPath, thirdPath, forthPath, PusPrimul, Human1, Pus2, Human2, Pus3, Human3, Pus4, Human4, Cos, Parcare;

    public void buildPaths() {
        // First path chain - just Line 1
        firstPath = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.500, 65.550, Point.CARTESIAN),
                                new Point(35.550, 65.550, Point.CARTESIAN)
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
                                new Point(35.550, 65.550, Point.CARTESIAN),
                                new Point(10.400, 32.566, Point.CARTESIAN),
                                new Point(85.957, 14.178, Point.CARTESIAN),
                                new Point(51.175, 50.068, Point.CARTESIAN),
                                new Point(49.182, 14.622, Point.CARTESIAN),
                                new Point(72.412, 23.926, Point.CARTESIAN),
                                new Point(30.591, 21.268, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(6)
                // Line 3
                .addPath(
                        new BezierCurve(
                                new Point(30.591, 21.268, Point.CARTESIAN),
                                new Point(69.108, 33.452, Point.CARTESIAN),
                                new Point(53.391, 0.443, Point.CARTESIAN),
                                new Point(63.360, 12.406, Point.CARTESIAN),
                                new Point(24.369, 11.298, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-22.5))
                .setZeroPowerAccelerationMultiplier(6)  // 10 second timeout for the entire path
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
                                new Point(24.369, 11.298, Point.CARTESIAN),
                                new Point(25.255, 25.698, Point.CARTESIAN),
                                new Point(9.300, 29.363, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-22.5), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2)
                .build();
        PusPrimul = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(9.300, 29.363, Point.CARTESIAN),
                                new Point(25.477, 46.080, Point.CARTESIAN),
                                new Point(35.398, 65.525, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(5))
                .setZeroPowerAccelerationMultiplier(5)
                .build();
        Human1 = follower.pathBuilder()
                .addPath( new BezierCurve(
                                new Point(35.398, 65.525, Point.CARTESIAN),
                                new Point(34.338, 37.883, Point.CARTESIAN),
                                new Point(9.700, 31.052, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(2.2)
                .build();
        Pus2 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(9.700, 31.052, Point.CARTESIAN),
                                new Point(25.477, 46.080, Point.CARTESIAN),
                                new Point(35.498, 64.025, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(5))
                .setZeroPowerAccelerationMultiplier(5)
                .build();
        Human2 = follower.pathBuilder()
                .addPath( new BezierCurve(
                                new Point(35.498, 64.025, Point.CARTESIAN),
                                new Point(34.338, 37.883, Point.CARTESIAN),
                                new Point(10.500, 31.052, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(3)
                .build();
        Pus3 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(10.500, 31.052, Point.CARTESIAN),
                                new Point(25.477, 46.080, Point.CARTESIAN),
                                new Point(36.198, 60.025, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(10))
                .setZeroPowerAccelerationMultiplier(5)
                .build();
        Human3 = follower.pathBuilder()
                .addPath( new BezierCurve(
                                new Point(36.198, 60.025, Point.CARTESIAN),
                                new Point(34.338, 37.883, Point.CARTESIAN),
                                new Point(11.500, 29.052, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(3)
                .build();
        Pus4 = follower.pathBuilder()
                //linia7
                .addPath(
                        new BezierCurve(
                                new Point(11.500, 29.052, Point.CARTESIAN),
                                new Point(25.477, 46.080, Point.CARTESIAN),
                                new Point(36.198, 58.025, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(10))
                .setZeroPowerAccelerationMultiplier(5)
                .build();
        Human4 = follower.pathBuilder()
                .addPath( new BezierCurve(
                                new Point(36.198, 58.025, Point.CARTESIAN),
                                new Point(23.040, 53.169, Point.CARTESIAN),
                                new Point(18.040, 48.726, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(-94))
                .setZeroPowerAccelerationMultiplier(4)
                .build();
        Cos = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(18.040, 48.726, Point.CARTESIAN),
                                new Point(20.382, 95.483, Point.CARTESIAN),
                                new Point(6.242, 123.271, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-94), Math.toRadians(-31))
                .setZeroPowerAccelerationMultiplier(4)
                .build();
        Parcare = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                            new Point(6.242, 123.271, Point.CARTESIAN),
                            new Point(14.748, 54.751, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-32), Math.toRadians(-120))
                .setZeroPowerAccelerationMultiplier(20)
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
                        s.kdf(4450);
                        mid();
                    }).start();
                    follower.followPath(secondPath, 1,/* holdEnd */ true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.kdf(100);
                        luat();
                    }).start();
                    s.kdf(360);
                    follower.followPath(forthPath, 1,/* holdEnd */ true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    new Thread(()->{
                        s.ghearaIn.setPosition(p.gheara_in_deschis);
                        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
                        punere_gard();
                    }).start();
                    follower.followPath(PusPrimul, 1,/* holdEnd */ true);
//                    s.brat_dr.setPosition(p.brat_dr_init);
//                    s.brat_st.setPosition(p.brat_st_init);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy()){
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                        s.kdf(50);
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
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                        s.kdf(50);
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
                    new Thread(()->{
                        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                        s.kdf(50);
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
                    new Thread(()->{  //miti o are mica, stefan i-o suge bine
                        s.kdf(100);
                        retragereSliderSteroizi_ultima();
                    }).start();
                    new Thread(() ->{
                        s.kdf(600);
                        mid_transfer();
                    }).start();
                    follower.followPath(Human4, 1,/* holdEnd */ false);
                    setPathState(12);
                }
                break;
            case 12:
                if (!follower.isBusy()){
                    s.kdf(150);
                    luat_transfer();
                    s.kdf(150);
                    new Thread(()->{
                        s.kdf(1150);
                        s.bratDreapta.setPosition(Pozitii.bratDreapta_cos);
                        s.bratStanga.setPosition(Pozitii.bratStanga_cos);
                        s.planulB.setPosition(Pozitii.planulB_inchis);
                        s.incheieturaout.setPosition(Pozitii.incheietura_cos);
                        ceva2 = true;
                        ansamblul_leleseana(-1808, 5000, 2,false);
                        ceva2 = false;
                        s.kdf(200);
                        s.ext1.setPosition(p.ext_extins);
                        s.ext2.setPosition(p.ext_extins);
                        s.brat_st.setPosition(p.turela_intrare_sub_st);
                        s.brat_dr.setPosition(p.turela_intrare_sub_dr);
                        s.incheietura.setPosition(p.incheieturaIN_tranfer);
                        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
                    }).start();
                    follower.followPath(Cos, 1,/* holdEnd */ true);
                    s.kdf(400);
                    setPathState(13);
                }
                break;
            case 13:
                if (!follower.isBusy()){
                    s.kdf(100);
                    new Thread(()->{
                        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
                        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
                    }).start();
                    follower.followPath(Parcare, 1, /* holdEnd */ true);
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
        // Optional: reduce telemetry latency
        telemetry.setMsTransmissionInterval(11);
        // Set Limelight pipeline index (adjust as needed)
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
        new Thread(()->{
            s.kdf(50);
            s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
            s.bratStanga.setPosition(Pozitii.bratStanga_bara);
            s.kdf(1045);
            s.planulB.setPosition(Pozitii.planulB_deschis);
            s.incheieturaout.setPosition(Pozitii.incheietura_bara);
        }).start();
        ceva2 = true;
        ansamblul_leleseana(-1060, 5000, 0, true);
        ceva2 = false;



        // s.kdf(200);
    }
    public synchronized void ansamblul_leleseana(int poz1,int vel,double tolerance, boolean soft_start){
        if (poz1 > s.sliderD.getCurrentPosition()){
//            if (soft_start) {
//                while (sliderD.getCurrentPosition() > -100 && this.opMode.opModeIsActive()) {
//                    sliderD.setVelocity(700);
//                    sliderS.setVelocity(700);
//                }
//            }
            while (s.sliderD.getCurrentPosition() < poz1 && !stop){
                s.sliderD.setVelocity(vel);
                s.sliderS.setVelocity(vel);

//                slider4.setVelocity(vel);

            }
        }
        else {
            while (s.sliderD.getCurrentPosition() > poz1 + tolerance && !stop){
                s.sliderD.setVelocity(-vel);
                s.sliderS.setVelocity(-vel);

                //  slider4.setVelocity(-vel);
            }
        }
        s.sliderD.setVelocity(0);
        s.sliderS.setVelocity(0);

        // slider4.setVelocity(0);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        new Thread(()->{
//            sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            ceva = true;
//        }).start();
    }
    public synchronized void punere_gard_simpla(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(125);
        ceva2 = true;
        ansamblul_leleseana(-1055, 5000, 0, true);
        ceva2 = false;
        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
        s.planulB.setPosition(Pozitii.planulB_deschis);
        s.kdf(50);
        s.incheieturaout.setPosition(Pozitii.incheietura_bara);
    }
    public synchronized void mid(){
        s.rotire.setPosition(p.poz_rotire_ciung);
        s.ext1.setPosition(0.3756);
        s.ext2.setPosition(0.3756);
        s.brat_dr.setPosition(p.brat_dr_extindere);
        s.brat_st.setPosition(p.brat_st_extindere);
        s.incheietura.setPosition(p.incheiatura_luare);
        //s.ghearaO.setPosition(Pozitii.gheara_deschis);
    }
    public synchronized void mid_transfer(){
        s.rotire.setPosition(p.poz_rotire_ciung);
        s.brat_dr.setPosition(p.brat_dr_extindere);
        s.brat_st.setPosition(p.brat_st_extindere);
        s.ext1.setPosition(p.ext_extins);
        s.ext2.setPosition(p.ext_extins);
        s.incheietura.setPosition(p.incheiatura_luare);
        //s.ghearaO.setPosition(Pozitii.gheara_deschis);
    }
    public synchronized void luat(){
        s.brat_st.setPosition(p.brat_st_luare); // 468
        s.brat_dr.setPosition(p.brat_dr_luare); // 496
        s.kdf(125);
        s.ghearaIn.setPosition(p.gheara_in_inchis);
        //retragere_ext();
        s.kdf(75);
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
            while (s.brat_dr.getPosition() > 0.214 && !stop) { //24
                poz_dr = poz_dr - 0.012;
                poz_st = poz_st + 0.012;
                s.brat_dr.setPosition(poz_dr);
                s.brat_st.setPosition(poz_st);
            }
            s.kdf(100);
            s.brat_dr.setPosition(0.16);
            s.brat_st.setPosition(0.88);
        }).start();
    }
    public synchronized void luat_transfer(){
        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
        s.brat_st.setPosition(p.brat_st_luare); // 468
        s.brat_dr.setPosition(p.brat_dr_luare); // 496
        s.kdf(50);
        s.ghearaIn.setPosition(p.gheara_in_inchis);

        //retragere_ext();
        new Thread(()->{
            s.kdf(150);
            s.ext1.setPosition(p.ext_inchis);
            s.ext2.setPosition(p.ext_inchis);
            s.incheietura.setPosition(p.incheieturaIN_tranfer);
            s.brat_st.setPosition(p.brat_st_transfer); // 468
            s.brat_dr.setPosition(p.brat_dr_transfer);
            s.kdf(600);
            s.ghearaOut.setPosition(Pozitii.ghera_strangulat);
            s.kdf(500);
            s.ghearaIn.setPosition(p.gheara_in_deschis);
        }).start();



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
        s.kdf(250);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
        //s.bratOut2.setPosition(p.out_gard_tele);
//                    s.incheietura_out.setPosition(p.incheietura_gard_tele);
        //s.retragere_slider();
        ceva2 = true;
        while (!s.taci_slider.isPressed() && !stop) {
            s.sliderD.setVelocity(5000);
            s.sliderS.setVelocity(5000);

        }
        s.sliderD.setVelocity(0);
        s.sliderS.setVelocity(0);
        s.kdf(200);
        s.planulB.setPosition(Pozitii.planulB_luare_gard);
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
    public synchronized void retragereSliderSteroizi_prima(){
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(250);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
        //s.bratOut2.setPosition(p.out_gard_tele);
//                    s.incheietura_out.setPosition(p.incheietura_gard_tele);
        //s.retragere_slider();
        ceva2 = true;
        while (!s.taci_slider.isPressed() && !stop) {
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
        s.ghearaOut.setPosition(Pozitii.gheara_deschis);


    }
    public synchronized void retragereSliderSteroizi_ultima(){
        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
        s.planulB.setPosition(Pozitii.planulB_inchis);
        s.kdf(50);
        s.bratDreapta.setPosition(Pozitii.bratDreapta_transfer);
        s.bratStanga.setPosition(Pozitii.bratStanga_transfer);
        s.incheieturaout.setPosition(Pozitii.incheietura_out_tranfer);
        //s.bratOut2.setPosition(p.out_gard_tele);
//                    s.incheietura_out.setPosition(p.incheietura_gard_tele);
        //s.retragere_slider();
        ceva2 = true;
        while (!s.taci_slider.isPressed() && !stop) {
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
        s.kdf(100);
        s.ghearaOut.setPosition(Pozitii.gheara_deschis);


    }
    public synchronized void sculare(){
        //s.incheietura.setPosition(p.incheiatura_luare);
        ceva2 = true;
        s.punere_lelese(-1040, 5000, 0, true);
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
