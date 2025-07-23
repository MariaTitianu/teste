
package FunctiiSistem;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class sistemeAutonomAmerica {
    public DcMotorEx sliderD,urcareStanga,urcareDreapta, sliderS, motorBL, motorBR, motorFL, motorFR;
    public TouchSensor taci_slider;
    public ServoImplEx ext1, ext2, ghearaIn, rotire,bratStanga,bratDreapta,incheieturaout, brat_st, brat_dr, incheietura, planulB;
    public Servo ghearaOut;
    //public RevColorSensorV3 colorIn, colorCos, colorOut;
    public VoltageSensor batteryVoltage;
    public PidControllerAdevarat pidSlider = new PidControllerAdevarat(0, 0, 0);
    public PidControllerAdevarat pidBrat = new PidControllerAdevarat(0,0,0);
    //  private boolean isStopRequested = false;
    // LinearOpMode opMode;
    boolean ceva = false, altceva = false, ceva2 = false, altceva2 = false;
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    //public Limelight3A limelight;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private boolean sasiuInited;
    public double timp = 0;

    private boolean isStopRequested = false;
    OpMode opMode;
    public sistemeAutonomAmerica(OpMode opMode){
        this.opMode = opMode;
    }

//    public sistemeAutonomTrump(OpMode opMode){
//        this.opMode = opMode;
//    }

    public void init(HardwareMap hard){
        this.init(hard, null, false);
    }

    public void init(HardwareMap hard, Telemetry telemetry, boolean shouldInitSasiu) {
        this.hardwareMap = hard;
        this.telemetry = telemetry;

        if (shouldInitSasiu) {
            initSasiu(hard);
        }
        sasiuInited = shouldInitSasiu;

        sliderD = hard.get(DcMotorEx.class, "sliderdreapta");
        sliderS = hard.get(DcMotorEx.class, "sliderstanga");
//        slider3 = hard.get(DcMotorEx.class, "motorjos");
        urcareStanga = hard.get(DcMotorEx.class, "urcareStanga");
        urcareDreapta = hard.get(DcMotorEx.class, "urcareDreapta");
        // slider4 = hard.get(DcMotorEx.class, "motor4");
        ext1 = hard.get(ServoImplEx.class, "extst");
        ext2 = hard.get(ServoImplEx.class, "extdr");
        ghearaIn = hard.get(ServoImplEx.class, "ghearain");
        rotire = hard.get(ServoImplEx.class, "rotire");
        brat_dr = hard.get(ServoImplEx.class, "brat_dreapta");
        brat_st = hard.get(ServoImplEx.class, "brat_stanga");
        bratStanga = hard.get(ServoImplEx.class, "bratStanga");
        bratDreapta = hard.get(ServoImplEx.class, "bratDreapta");
        //   bratIn2 = hard.get(ServoImplEx.class, "bratin2");
        //bratOut1 = hard.get(ServoImplEx.class, "bratstanga");
        //bratOut2 = hard.get(ServoImplEx.class, "bratdreapta");
        ghearaOut = hard.get(Servo.class, "ghearaout");
        incheieturaout = hard.get(ServoImplEx.class, "incheieturaout");
        incheietura = hard.get(ServoImplEx.class, "incheietura");
        // pula = hard.get(Servo.class, "pula");
        taci_slider = (TouchSensor) hard.get("tacidreapta");

        // colorIn = hard.get(RevColorSensorV3.class, "color");
        // colorCos = hard.get(RevColorSensorV3.class, "colorcos");
        // colorOut = hard.get(RevColorSensorV3.class, "colorout");
        planulB = hard.get(ServoImplEx.class, "planulb");

        //  limelight = hardwareMap.get(Limelight3A.class, "limelight");
        batteryVoltage = hard.voltageSensor.iterator().next();

        sliderS.setDirection(DcMotorEx.Direction.REVERSE);



        sliderD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        sliderS.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // slider4.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //  slider4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        //  slider4.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


    }

    public void stop(){
        this.isStopRequested = true;
    }

    public void initSasiu(HardwareMap hard) {
        motorBL = hard.get(DcMotorEx.class, "motorBL"); // Motor Back-Left
        motorBR = hard.get(DcMotorEx.class, "motorBR"); // Motor Back-Left
        motorFL = hard.get(DcMotorEx.class, "motorFL"); // Motor Back-Left
        motorFR = hard.get(DcMotorEx.class, "motorFR"); // Motor Back-Left

        motorBL.setDirection(DcMotorEx.Direction.REVERSE);
        motorFL.setDirection(DcMotorEx.Direction.REVERSE);

        motorBL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        motorFR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        sasiuInited = true;
    }


    public void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        aprilTag.setDecimation(2);

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    public void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while ((visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                kdf(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested) {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                kdf(50);
            }
            exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);
            kdf(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            kdf(20);
        }
    }

    public void initTaguriAprilie() {
        initAprilTag();
        setManualExposure(6, 250);
    }

    public void detectieTaguriAprilie(int DESIRED_TAG_ID) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                if (detection.id == DESIRED_TAG_ID) {
                    telemetry.addData("April tag detection corners:", detection.corners);
                    telemetry.update();
                    break;
                }
            }
            else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }
    }

    public void setIsStopRequested(boolean value){
        isStopRequested = value;
    }

    public void pozitii_init_bara(){
        // planulB.setPosition(0.25);
        planulB.setPosition(Pozitii.planulB_inchis);
        ext1.setPosition(p.ext_inchis);
        ext2.setPosition(p.ext_inchis);
        //  bratIn1.setPosition(p.bratIn_init_sigma);
        // bratIn2.setPosition(p.bratIn_extindere);
        incheieturaout.setPosition(Pozitii.incheietura_bara);
        incheietura.setPosition(1);
        ghearaIn.setPosition(p.gheara_in_deschis);
        ghearaOut.setPosition(Pozitii.gheara_inchis);
        bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        bratStanga.setPosition(Pozitii.bratStanga_bara);

        rotire.setPosition(p.poz_rotire_normal);
        // bratOut2.setPosition(p.out_bara);
        //incheietura_out.setPosition(p.incheoietura_out_bara_bagare);

    }

    public synchronized void poz_initializare_cos(){
        planulB.setPosition(Pozitii.planulB_inchis);
        ext1.setPosition(p.ext_inchis);
        ext2.setPosition(p.ext_inchis);
        //  bratIn1.setPosition(p.bratIn_init_sigma);
        // bratIn2.setPosition(p.bratIn_extindere);
        incheieturaout.setPosition(Pozitii.incheietura_bara);
        incheietura.setPosition(1);
        ghearaIn.setPosition(p.gheara_in_deschis);
        ghearaOut.setPosition(Pozitii.ghera_strangulat);
        bratDreapta.setPosition(Pozitii.bratDreapta_bara);
        bratStanga.setPosition(Pozitii.bratStanga_bara);

        rotire.setPosition(p.poz_rotire_normal);
        //incheietura_out.setPosition(p.incheietura_out_cos);
    }


    public synchronized void ansamblul_leleseana(int poz1,int vel,double tolerance, boolean soft_start){
        if (poz1 > sliderD.getCurrentPosition()){
//            if (soft_start) {
//                while (sliderD.getCurrentPosition() > -100 && this.opMode.opModeIsActive()) {
//                    sliderD.setVelocity(700);
//                    sliderS.setVelocity(700);
//                }
//            }
            while (sliderD.getCurrentPosition() < poz1 && !this.isStopRequested){
                sliderD.setVelocity(vel);
                sliderS.setVelocity(vel);

//                slider4.setVelocity(vel);

            }
        }
        else {
            while (sliderD.getCurrentPosition() > poz1 + tolerance && !this.isStopRequested){
                sliderD.setVelocity(-vel);
                sliderS.setVelocity(-vel);

                //  slider4.setVelocity(-vel);
            }
        }
        sliderD.setVelocity(0);
        sliderS.setVelocity(0);

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

    public synchronized void punere_lelese(int poz1,int vel,double tolerance, boolean soft_start){
        if (poz1 > sliderD.getCurrentPosition() && !isStopRequested){

            while (sliderD.getCurrentPosition() < poz1 && !isStopRequested ){
                sliderD.setVelocity(vel);
                sliderS.setVelocity(vel);

                //   slider4.setVelocity(vel);
            }
        }
        else {
            while (sliderD.getCurrentPosition() > poz1 + tolerance && !isStopRequested){
                sliderD.setVelocity(-vel);
                sliderS.setVelocity(-vel);

                // slider4.setVelocity(-vel);

            }
        }
        sliderD.setVelocity(0);
        sliderS.setVelocity(0);
        planulB.setPosition(Pozitii.planulB_deschis);
        // slider4.setVelocity(0);
    }
//    public synchronized void brat(){
//        brat1.setVelocity(5000);
//        brat2.setVelocity(5000);
//        while (!taci_brat2.isPressed()) {
//        }
//   i     brat1.setVelocity(0);
//        brat2.setVelocity(0);
////        try {
////            Thread.sleep(100);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//        brat1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        brat2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        brat1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        brat2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        altceva = true;
////        pidBrat.disable();
////        brat1.setMotorDisable();
////        brat2.setMotorDisable();
//    }
//    public synchronized void retragere_brat(){
//        altceva2 = true;
//        brat1.setVelocity(-5000);
//        brat2.setVelocity(-5000);
//        while (!taci_brat.isPressed() && this.opMode.opModeIsActive()) {
//        }
//        brat1.setVelocity(0);
//        brat2.setVelocity(0);
////        try {
////            Thread.sleep(100);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//        brat1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        brat2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        brat1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        brat2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        altceva2 = false;
//        altceva = true;
////        pidBrat.disable();
////        brat1.setMotorDisable();
////        brat2.setMotorDisable();
//    }
//    public synchronized void retragere_slider(){
//        ceva2 = true;
//        sliderD.setVelocity(5000);
//        sliderS.setVelocity(5000);
//        while (!taci_slider.isPressed() && this.opMode.opModeIsActive()) {
//        }
//        sliderD.setVelocity(0);
//        sliderS.setVelocity(0);
////        try {
////            Thread.sleep(100);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//        sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        ceva2 = false;
//        ceva = true;
////        pidSlider.disable();
////        sliderD.setMotorDisable();
////        sliderS.setMotorDisable();
//    }




    public void kdf(long t) {
        long lastTime = System.currentTimeMillis();
        while (lastTime + t > System.currentTimeMillis()){

        }
    }
}
