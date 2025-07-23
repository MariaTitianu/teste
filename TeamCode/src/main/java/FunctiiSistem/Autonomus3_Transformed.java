//package FunctiiSistem;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.PathChain;
//import com.pedropathing.pathgen.Point;
//import com.pedropathing.util.Timer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//
//import pedroPathing.constants.FConstants;
//import pedroPathing.constants.LConstants;
//
//@Autonomous(name = "Autosugus3 Transformed", group = "Competition")
//public class Autonomus3_Transformed extends OpMode {
//
//    sistemeAutonomTrump s = new sistemeAutonomTrump();
//    public PidControllerAdevarat pidSlider = new PidControllerAdevarat(0, 0, 0);
//    public Follower follower;
//    public boolean ceva = false, ceva2 = false, stop = false;
//    double pidResult, poz_dr, poz_st;
//    public Timer pathTimer, opmodeTimer;
//    public int pathState;
//
//    // Starting pose of the robot
//    public final Pose startPose = new Pose(135.5, 55.0, Math.toRadians(180));
//
//    // The single, continuous path for the entire autonomous routine
//    public PathChain firstPath, secondPath, forthPath, PusPrimul, remainingPath, thirdPath, LuatUltim, Parcare;
//
//    public void buildPaths() {
//        firstPath = follower.pathBuilder()
//                // #1: From Autonomus2
//                .addPath(
//                        new BezierLine(
//                                new Point(135.45, 55.0, Point.CARTESIAN),
//                                new Point(128.206, 14.186, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(150))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//        secondPath = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(
//                                new Point(128.206, 14.186, Point.CARTESIAN),
//                                new Point(126.406, 14.186, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(160))
//
//                .build();
//        PusPrimul = follower.pathBuilder()
//                // #3: From Autonomus2
//                .addPath(
//                        new BezierLine(
//                                new Point(126.406, 15.186, Point.CARTESIAN),
//                                new Point(128.606, 9.243, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(165), Math.toRadians(150))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//
//        thirdPath = follower.pathBuilder()
//                // #2: From Autonomus2
//                .addPath(
//                        // Line 2
//                        new BezierLine(
//                                new Point(128.606, 9.243, Point.CARTESIAN),
//                                new Point(125.005, 10.949, Point.CARTESIAN)
//                        )
//                )
//                .setZeroPowerAccelerationMultiplier(3)
//                .setLinearHeadingInterpolation(Math.toRadians(150), Math.toRadians(183.15))
//
//                .build();
//
//
//        forthPath = follower.pathBuilder()
//                // #3: From Autonomus2
//                .addPath(
//                        new BezierLine(
//                                new Point(125.105, 10.949, Point.CARTESIAN),
//                                new Point(128.606, 9.243, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(184), Math.toRadians(140))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//
//        LuatUltim = follower.pathBuilder()
//                // #4: From Autonomus2
//                .addPath(
//                        new BezierLine(
//                                new Point(128.606, 12.243, Point.CARTESIAN),
//                                new Point(121.954, 11.742, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(202))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//
//        remainingPath = follower.pathBuilder()
//                // #5: Start of the combined path
//                .addPath(
//                        new BezierLine(
//                                new Point(121.954, 11.742, Point.CARTESIAN),
//                                new Point(126.606, 9.243, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(202), Math.toRadians(138))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//        Parcare = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(
//                                new Point(127.606, 9.243, Point.CARTESIAN),
//                                new Point(88.748, 38.757, Point.CARTESIAN)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(90))
//                .setZeroPowerAccelerationMultiplier(4)
//                .build();
//
//    }
//
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.resetTimer();
//    }
//
//    public void autonomousPathUpdate() {
//        switch (pathState) {
//            case 0:
//                new Thread(this::sculare).start();
//                follower.followPath(firstPath, 1.0, true);
//                setPathState(1);
//                break;
//            case 1:
//                if (!follower.isBusy()) {
//                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//                    s.kdf(300);
//                    retragereSliderSteroizi_ultima();
//                    follower.followPath(secondPath, 1.0, true);
//                    setPathState(2);
//                    //}).start();
//
//
//                }
//                break;
//            case 2:
//                if (!follower.isBusy()){
//                    luat_transfer();
//                    s.kdf(1050);
//                    s.bratDreapta.setPosition(Pozitii.bratDreapta_cos);
//                    s.bratStanga.setPosition(Pozitii.bratStanga_cos);
//                    s.planulB.setPosition(Pozitii.planulB_inchis);
//                    s.incheieturaout.setPosition(Pozitii.incheietura_cos);
//                    ceva2 = true;
//                    s.ansamblul_leleseana(-1808, 5000, 2,false);
//                    ceva2 = false;
//                    s.kdf(200);
//                    mid2();
//                    follower.followPath(PusPrimul, 1.0, true);
//                    setPathState(3);
//                }
//
//            case 3:
//                if (!follower.isBusy()){
//                    //new Thread(()->{
//                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//                    s.kdf(300);
//                    follower.followPath(thirdPath, 1.0, true);
//                    setPathState(4);
//                }
//                break;
//            case 4:
//                if (!follower.isBusy()) {
//                    s.kdf(150);
//                    retragereSliderSteroizi_ultima();
//                    s.kdf(150);
//                    luat_transfer();
//                    s.kdf(1050);
//                    s.bratDreapta.setPosition(Pozitii.bratDreapta_cos);
//                    s.bratStanga.setPosition(Pozitii.bratStanga_cos);
//                    s.planulB.setPosition(Pozitii.planulB_inchis);
//                    s.incheieturaout.setPosition(Pozitii.incheietura_cos);
//                    ceva2 = true;
//                    s.ansamblul_leleseana(-1808, 5000, 2,false);
//                    ceva2 = false;
//                    s.kdf(200);
//                    follower.followPath(forthPath, 1.0, true);
//
//
////                    mid2();
////                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
////                    s.kdf(300);
////                    retragereSliderSteroizi_ultima();
////                    follower.turnToDegrees(-19);
////                    follower.followPath(PusPrimul, 1.0, true);
//                    setPathState(5);
//                }
//                break;
//            case 5:
//                if (!follower.isBusy()) {
//                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//                    s.kdf(300);
//                    new Thread(()-> {
//                        s.kdf(400);
//                        mid3();
//                        retragereSliderSteroizi_ultima();
//                    }).start();
//                    follower.followPath(LuatUltim, 1.0, true);
//                    setPathState(6);
//
//                }
//                break;
//            case 6:
//                if (!follower.isBusy()){
//                    new Thread(()-> {
//                        s.kdf(150);
//                        luat_transfer();
//                        s.kdf(1050);
//                        s.bratDreapta.setPosition(Pozitii.bratDreapta_cos);
//                        s.bratStanga.setPosition(Pozitii.bratStanga_cos);
//                        s.planulB.setPosition(Pozitii.planulB_inchis);
//                        s.incheieturaout.setPosition(Pozitii.incheietura_cos);
//                        ceva2 = true;
//                        s.ansamblul_leleseana(-1808, 5000, 2,false);
//                        ceva2 = false;
//                        s.kdf(200);
//                    }).start();
//                    s.kdf(3000);
//                    follower.followPath(remainingPath, 1.0, true);
//                    setPathState(7);
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()){
//                    s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//                    s.kdf(300);
//                    new Thread(()-> {
//                        s.kdf(550);
//                        retragereSliderSteroizi_ultima();
//                    }).start();
//                    follower.followPath(Parcare, 1.0, true);
//                    setPathState(8);
//                }
//                break;
//            case 8:
//                if (!follower.isBusy()) {
//                    setPathState(-1);
//                }
//                break;
//        }
//    }
//
//
//    @Override
//    public void init() {
//        s.init(hardwareMap);
//        s.poz_initializare_cos();
//        s.brat_dr.setPosition(0.292);
//        s.brat_st.setPosition(0.818);
//        pathTimer = new Timer();
//        opmodeTimer = new Timer();
//        opmodeTimer.resetTimer();
//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
//        follower.setStartingPose(startPose);
//        buildPaths();
//        pid_slider.start();
//        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
//        telemetry.setMsTransmissionInterval(50);
//        telemetry.addLine("Initialization Complete. Ready for Start.");
//        telemetry.update();
//    }
//
//    @Override
//    public void init_loop() {}
//
//    @Override
//    public void start() {
//        opmodeTimer.resetTimer();
//        setPathState(0);
//    }
//
//    @Override
//    public void loop() {
//        follower.update();
//        autonomousPathUpdate();
//
//        telemetry.addData("isFollowing", follower.isBusy());
//        telemetry.addData("pathState", pathState);
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading (deg)", Math.toDegrees(follower.getPose().getHeading()));
//        telemetry.update();
//    }
//
//
//    @Override
//    public void stop() {
//        stop = true;
//        follower.breakFollowing();
//    }
//
//    // All the original synchronized methods remain unchanged, with p. -> Pozitii. fixes
//    public synchronized void punere_gard(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(50);
//        new Thread(()->{
//            s.kdf(50);
//            s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
//            s.bratStanga.setPosition(Pozitii.bratStanga_bara);
//            s.kdf(505);
//            s.planulB.setPosition(Pozitii.planulB_deschis);
//            s.incheieturaout.setPosition(Pozitii.incheietura_bara);
//        }).start();
//        ceva2 = true;
//        s.ansamblul_leleseana(-1060, 5000, 0, true);
//        ceva2 = false;
//    }
//    public synchronized void punere_gard_detectie(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(50);
//        ceva2 = true;
//        s.ansamblul_leleseana(-1060, 5000, 0, true);
//        ceva2 = false;
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
//        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
//        s.kdf(450);
//        s.planulB.setPosition(Pozitii.planulB_deschis);
//        s.incheieturaout.setPosition(Pozitii.incheietura_bara);
//    }
//    public synchronized void punere_gard_simpla(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(125);
//        ceva2 = true;
//        s.ansamblul_leleseana(-1055, 5000, 0, true);
//        ceva2 = false;
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_bara);
//        s.bratStanga.setPosition(Pozitii.bratStanga_bara);
//        s.planulB.setPosition(Pozitii.planulB_deschis);
//        s.kdf(50);
//        s.incheieturaout.setPosition(Pozitii.incheietura_bara);
//    }
//    public synchronized void mid(){
//        s.rotire.setPosition(p.poz_rotire_ciung);
//        s.ext1.setPosition(p.ext_extins);
//        s.ext2.setPosition(p.ext_extins);
//        s.brat_dr.setPosition(p.brat_dr_extindere);
//        s.brat_st.setPosition(p.brat_st_extindere);
//        s.incheietura.setPosition(p.incheiatura_luare);
//    }
//    public synchronized void mid2(){
//        s.rotire.setPosition(p.poz_rotire_ciung);
//        s.ext1.setPosition(0.41);
//        s.ext2.setPosition(0.41);
//        s.brat_dr.setPosition(p.brat_dr_extindere);
//        s.brat_st.setPosition(p.brat_st_extindere);
//        s.incheietura.setPosition(p.incheiatura_luare);
//    }
//    public synchronized void mid3(){
//        s.rotire.setPosition(p.poz_rotire_ciung);
//        s.ext1.setPosition(0.385);
//        s.ext2.setPosition(0.385);
//        s.brat_dr.setPosition(p.brat_dr_extindere);
//        s.brat_st.setPosition(p.brat_st_extindere);
//        s.incheietura.setPosition(p.incheiatura_luare);
//    }
//
//    public synchronized void luat(){
//        s.brat_st.setPosition(p.brat_st_luare);
//        s.brat_dr.setPosition(p.brat_dr_luare);
//        s.kdf(50);
//        s.ghearaIn.setPosition(p.gheara_in_inchis);
//        new Thread(()->{
//            s.kdf(150);
//            s.ext1.setPosition(p.ext_inchis);
//            s.ext2.setPosition(p.ext_inchis);
//            s.incheietura.setPosition(p.incheietura_human);
//            s.planulB.setPosition(Pozitii.planulB_luare_gard);
//            s.brat_st.setPosition(0.587);
//            s.brat_dr.setPosition(0.543);
//            s.rotire.setPosition(0.43);
//            s.kdf(100);
//            poz_dr = s.brat_dr.getPosition();
//            poz_st = s.brat_st.getPosition();
//            while (s.brat_dr.getPosition() > 0.214) {
//                poz_dr = poz_dr - 0.012;
//                poz_st = poz_st + 0.012;
//                s.brat_dr.setPosition(poz_dr);
//                s.brat_st.setPosition(poz_st);
//            }
//        }).start();
//    }
//    public synchronized void luat_transfer(){
//        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//        s.brat_st.setPosition(p.brat_st_luare);
//        s.brat_dr.setPosition(p.brat_dr_luare);
//        s.kdf(150);
//        s.ghearaIn.setPosition(p.gheara_in_inchis);
//        new Thread(()->{
//            s.kdf(150);
//            s.ext1.setPosition(p.ext_inchis);
//            s.ext2.setPosition(p.ext_inchis);
//            s.incheietura.setPosition(p.incheieturaIN_tranfer);
//            s.brat_st.setPosition(p.brat_st_transfer);
//            s.brat_dr.setPosition(p.brat_dr_transfer);
//            s.kdf(500);
//            s.ghearaOut.setPosition(Pozitii.ghera_strangulat);
//            s.kdf(500);
//            s.ghearaIn.setPosition(p.gheara_in_deschis);
//        }).start();
//    }
//
//    public double clamp(double val, double min, double max) {
//        return Math.max(min, Math.min(max, val));
//    }
//    public synchronized void retragereSlider(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(50);
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
//        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
//        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
//        ceva2 = true;
//        while (!s.taci_slider.isPressed()) {
//            s.sliderD.setVelocity(5000);
//            s.sliderS.setVelocity(5000);
//        }
//        s.sliderD.setVelocity(0);
//        s.sliderS.setVelocity(0);
//        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//        new Thread(()->{
//            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            ceva2 = false;
//            ceva = true;
//        }).start();
//    }
//    public synchronized void retragereSliderSteroizi(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(250);
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
//        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
//        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
//        ceva2 = true;
//        while (!s.taci_slider.isPressed()) {
//            s.sliderD.setVelocity(5000);
//            s.sliderS.setVelocity(5000);
//        }
//        s.sliderD.setVelocity(0);
//        s.sliderS.setVelocity(0);
//        new Thread(()->{
//            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            ceva2 = false;
//            ceva = true;
//        }).start();
//        s.kdf(200);
//        s.planulB.setPosition(Pozitii.planulB_luare_gard);
//        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//    }
//    public synchronized void retragereSliderSteroizi_prima(){
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(250);
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_luare_gard);
//        s.bratStanga.setPosition(Pozitii.bratStanga_luare_gard);
//        s.incheieturaout.setPosition(Pozitii.incheietura_luare_gard);
//        ceva2 = true;
//        while (!s.taci_slider.isPressed()) {
//            s.sliderD.setVelocity(5000);
//            s.sliderS.setVelocity(5000);
//        }
//        s.sliderD.setVelocity(0);
//        s.sliderS.setVelocity(0);
//        new Thread(()->{
//            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            ceva2 = false;
//            ceva = true;
//        }).start();
//        s.kdf(50);
//        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//    }
//    public synchronized void retragereSliderSteroizi_ultima(){
//        s.ghearaOut.setPosition(Pozitii.gheara_inchis);
//        s.planulB.setPosition(Pozitii.planulB_inchis);
//        s.kdf(50);
//        s.bratDreapta.setPosition(Pozitii.bratDreapta_transfer);
//        s.bratStanga.setPosition(Pozitii.bratStanga_transfer);
//        s.incheieturaout.setPosition(Pozitii.incheietura_out_tranfer);
//        ceva2 = true;
//        while (!s.taci_slider.isPressed()) {
//            s.sliderD.setVelocity(5000);
//            s.sliderS.setVelocity(5000);
//        }
//        s.sliderD.setVelocity(0);
//        s.sliderS.setVelocity(0);
//        new Thread(()->{
//            s.sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            s.sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            s.sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//            ceva2 = false;
//            ceva = true;
//        }).start();
//        s.kdf(100);
//        s.ghearaOut.setPosition(Pozitii.gheara_deschis);
//    }
//    public synchronized void sculare(){
//        //s.punere_lelese(-1040, 5000, 5, true);
//        new Thread(()->{
//            s.bratDreapta.setPosition(Pozitii.bratDreapta_cos);
//            s.bratStanga.setPosition(Pozitii.bratStanga_cos);
//            s.planulB.setPosition(Pozitii.planulB_inchis);
//            s.incheieturaout.setPosition(Pozitii.incheietura_cos);
//        }).start();
//        ceva2 = true;
//        s.ansamblul_leleseana(-1800,5000,5,true);
//        mid();
//        ceva2 = false;
//    }
//
//    public final Thread pid_slider = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            pidSlider.enable();
//            while(!stop){
//                pidSlider.setPID(Config.pslider, Config.islider, Config.dslider);
//                if(ceva2){
//                    ceva = true;
//                }
//                else{
//                    if(ceva){
//                        ceva = false;
//                        pidSlider.setSetpoint(s.sliderD.getCurrentPosition());
//                    }
//                    if (s.taci_slider.isPressed()){
//                        s.sliderD.setPower(0);
//                        s.sliderS.setPower(0);
//                    }
//                    else {
//                        pidResult = pidSlider.performPID(s.sliderD.getCurrentPosition());
//                        s.sliderD.setPower(pidResult);
//                        s.sliderS.setPower(pidResult);
//                    }
//                }
//            }
//        }
//    });
//}