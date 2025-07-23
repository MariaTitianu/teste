package FunctiiSistem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Servo;



public class sistemeTeleOp {
    public DcMotorEx sliderS, sliderD, urcareStanga, urcareDreapta;
    public TouchSensor touch_slider, taci_brat, taci_brat2;
    public ServoImplEx ext1, ext2, ghearaIn, rotire, bratIN, turela, bratOut1, incheietura, planulB, incheietura_out, bratDreapta, bratStanga,incheieturaout ;
    public Servo ghearaOut;
   // public RevColorSensorV3 colorIn, colorCos, colorOut;
    public PidControllerAdevarat pidSlider = new PidControllerAdevarat(0, 0, 0);
  //  public PidControllerAdevarat pidBrat = new PidControllerAdevarat(0,0,0);
  //  private boolean isStopRequested = false;
   // LinearOpMode opMode;
    boolean ceva = false, altceva = false;

    public void initSistem(HardwareMap hard) {
        sliderD = hard.get(DcMotorEx.class, "sliderdreapta");
        sliderS = hard.get(DcMotorEx.class, "sliderstanga");
        urcareStanga = hard.get(DcMotorEx.class, "urcareStanga");
        urcareDreapta = hard.get(DcMotorEx.class, "urcareDreapta");
        ext1 = hard.get(ServoImplEx.class, "extst");
        ext2 = hard.get(ServoImplEx.class, "extdr");
        ghearaIn = hard.get(ServoImplEx.class, "ghearain");
        rotire = hard.get(ServoImplEx.class, "rotire");
        bratIN = hard.get(ServoImplEx.class, "brat_intake");
        turela = hard.get(ServoImplEx.class, "turela");
        //  bratIn2 = hard.get(ServoImplEx.class, "bratin2");
        bratStanga = hard.get(ServoImplEx.class, "bratStanga");
        bratDreapta = hard.get(ServoImplEx.class, "bratDreapta");
        ghearaOut = hard.get(ServoImplEx.class, "ghearaout");
        incheietura = hard.get(ServoImplEx.class, "incheietura");
        incheieturaout = hard.get(ServoImplEx.class, "incheieturaout");
       // pula = hard.get(Servo.class, "pula");
        touch_slider = (TouchSensor) hard.get("tacidreapta");
        taci_brat2 = (TouchSensor) hard.get("taci2");
        taci_brat = (TouchSensor) hard.get("tacibrat");
       // colorIn = hard.get(RevColorSensorV3.class, "color");
       // colorCos = hard.get(RevColorSensorV3.class, "colorcos");
       // colorOut = hard.get(RevColorSensorV3.class, "colorout");
        planulB = hard.get(ServoImplEx.class, "planulb");
//        incheietura_out = hard.get(ServoImplEx.class, "incheieturaspate");


        sliderS.setDirection(DcMotorEx.Direction.REVERSE);
        urcareDreapta.setDirection(DcMotorEx.Direction.REVERSE);

        sliderD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        sliderS.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        urcareDreapta.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        urcareStanga.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        urcareDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        urcareStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        urcareDreapta.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        urcareStanga.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void extindere_ext(){
        ext1.setPosition(p.ext_extins);
        ext2.setPosition(p.ext_extins);
     //   bratIn1.setPosition(p.bratIn_extindereTele);
       // bratIn2.setPosition(p.bratIn_extindereTele);

        //incheietura.setPosition(p.incheietura_submarine);
    }
    public void extindere_legal(){
        ext1.setPosition(p.ext_mid);
        ext2.setPosition(p.ext_mid);
    }





//    public void brat(){
//        brat1.setVelocity(5000);
//        brat2.setVelocity(5000);
//        while (!taci_brat2.isPressed()) {
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
//        altceva = true;
////        pidBrat.disable();
////        brat1.setMotorDisable();
////        brat2.setMotorDisable();
//    }
//    public  void brat(int poz1,int vel,double tolerance){
//        pidBrat.enable();
//        if (poz1 > brat1.getCurrentPosition()){
//            while (brat1.getCurrentPosition() < poz1){
//                brat1.setVelocity(vel);
//                brat2.setVelocity(vel);
//            }
//
//        }
//        else {
//            while (brat1.getCurrentPosition()>poz1 + tolerance ){
//                brat1.setVelocity(-vel);
//                brat2.setVelocity(-vel);
//            }
//        }
//        brat1.setVelocity(0);
//        brat2.setVelocity(0);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        brat1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        brat2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        altceva = true;
//    }
//    public void retragere_brat(){
//        brat1.setVelocity(-5000);
//        brat2.setVelocity(-5000);
//        while (!taci_brat.isPressed()) {
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
//        altceva = true;
////        pidBrat.disable();
////        brat1.setMotorDisable();
////        brat2.setMotorDisable();
//    }

    public void retragere_slider(){
        while (!touch_slider.isPressed()) {
                    sliderD.setVelocity(5000);
                    sliderS.setVelocity(5000);
                }
                sliderS.setVelocity(0);
                sliderD.setVelocity(0);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        new Thread(()->{
            sliderS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sliderD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            sliderS.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            sliderD.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            ceva = true;
        }).start();

    }

}
