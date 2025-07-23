package FunctiiSistem;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;;
@TeleOp
public class Servo_Outtake extends OpMode {
    public ServoImplEx ext1, ext2,brat_st,brat_dr;
    PidControllerAdevarat pidSlider = new PidControllerAdevarat(0,0,0);

    double poz1 = 0.5, poz2 = 0, poz3 = 0.5, poz4 = 0.5,  poz6=0.5, poz7 = 0.5;
    boolean pid = false;
    double pidResultS;
    sistemeTeleOp s = new sistemeTeleOp();

    @Override
    public void init() {
        s.initSistem(hardwareMap);
        pidSlider.enable();
        //s.poz_initializare();
    }


    @Override
    public void loop() {
        pidSlider.setPID(Config.pslider, Config.islider, Config.dslider);

        if (gamepad2.left_stick_y != 0.0) {
            s.sliderD.setPower(gamepad2.left_stick_y);
            s.sliderS.setPower(gamepad2.left_stick_y);
            pid = true;
        } else {
            if (pid) {
                pid = false;
                pidSlider.setSetpoint(s.sliderD.getCurrentPosition());
            }
            if (s.touch_slider.isPressed()) {
                pidSlider.setSetpoint(0);
                s.sliderD.setPower(0);
                s.sliderS.setPower(0);
            } else {
                pidResultS = pidSlider.performPID(s.sliderD.getCurrentPosition());
                //  s.slider1.setPower(pidResultS);
                //  s.slider2.setPower(pidResultS);
                s.sliderD.setPower(pidResultS);
                s.sliderS.setPower(pidResultS);
            }
        }

//        if (gamepad1.right_trigger > 0 && poz < 1.0){
//            while(poz < 0.972){
//                poz+= 0.03;
//                try {
//                    Thread.sleep(45);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                servo_fudul.setPosition(poz);
//            };
//
//        }
//        if (gamepad1.left_trigger > 0 && poz > 0){
//            poz -= 0.0001;
//            servo_fudul.setPosition(poz);
//
//        }
        if (gamepad2.y && poz1>0){
            poz1-=0.001;
        }
        if (gamepad2.a && poz1<1){
            poz1+=0.001;
        }
        s.bratDreapta.setPosition(poz1);
        s.bratStanga.setPosition(poz1);

        if (gamepad2.dpad_right && poz4>0){
            poz4-=0.001;
        }
        if (gamepad2.dpad_left && poz4<1){
            poz4+=0.001;
        }
        s.incheieturaout.setPosition(poz4);

        if (gamepad2.right_bumper && poz6>0){
            poz6-=0.001;
        }
        if (gamepad2.left_bumper && poz6<1){
            poz6+=0.001;
        }
        s.planulB.setPosition(poz6);

        if (gamepad2.x && poz7>0){
            poz7-=0.001;
        }
        if (gamepad2.b && poz7<1){
            poz7+=0.001;
        }
        s.ghearaOut.setPosition(poz7);
//
//



        //   s.ghearaOut.setPosition(p.gheara_out_deschis);

        // s.bratIn2.setPosition(poz2);  //
//        s.bratOut2.setPosition(poz);
//        if (gamepad2.dpad_left && poz3<1){
//            poz3+=0.001;
//        }
//        if (gamepad2.dpad_right && poz3>0){
//            poz3-=0.001;
//        }
//        s.incheietura.setPosition(poz3);
//        if (gamepad2.touchpad) s.ghearaOut.setPosition(p.poz_out_inchis);
////

        // telemetry.addData("stanga", poz2);


        telemetry.addData("incheietura ", s.incheieturaout.getPosition()); //0.33  //0.668, 0.633
        telemetry.addData("brat dreapta", s.bratDreapta.getPosition());
        telemetry.addData("brat stanga", s.bratStanga.getPosition());
//        telemetry.addData("gheara", s.ghearaIn.getPosition());
//        telemetry.addData("brat out", s.bratOut1.getPosition());
        telemetry.addData("gheara out", s.ghearaOut.getPosition());
//        telemetry.addData("incheietura out", s.incheietura_out.getPosition());
//        telemetry.addData("extendo", s.ext1.getPosition());
        telemetry.addData("planul B", s.planulB.getPosition());
//        telemetry.addData("planul b", s.planulB.getPosition());
        // telemetry.addData("rotire", poz2); //0.54  //
        telemetry.addData("poz slider S", s.sliderS.getCurrentPosition());
        telemetry.addData("poz slider D", s.sliderD.getCurrentPosition());
        telemetry.update();
    }
}