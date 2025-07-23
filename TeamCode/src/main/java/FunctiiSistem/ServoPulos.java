package FunctiiSistem;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;;
@TeleOp
public class ServoPulos extends OpMode {
    public ServoImplEx  brat_st,brat_dr;

    double poz1 = 0.5, poz2 = 0, poz3 = 0.5, poz4 = 0.5,  poz6=0.5, poz7 = 1, poz8 = 0.5, poz9 = 0.5;
    sistemeTeleOp s = new sistemeTeleOp();

    @Override
    public void init() {
        s.initSistem(hardwareMap);
        //s.poz_initializare();
    }


    @Override
    public void loop() {

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
        //s.planulB.setPosition(poz6);

        if (gamepad2.x && poz7>0){
            poz7-=0.001;
        }
        if (gamepad2.b && poz7<1){
            poz7+=0.001;
        }
        s.incheietura.setPosition(poz7);
//
//
        if (gamepad2.right_trigger>0 && poz8>0){
            poz8-=0.001;
            poz9+=0.001;
        }
        if (gamepad2.left_trigger>0 && poz8<1){
            poz8+=0.001;
            poz9-=0.001;
        }
        //s.brat_st.setPosition(poz8);
        //s.brat_dr.setPosition(poz9);

        if (gamepad2.dpad_down && poz8>0){
            poz8-=0.001;
            poz9-=0.001;
        }
        if (gamepad2.dpad_up && poz8<1){
            poz8+=0.001;
            poz9+=0.001;
        }
        //s.brat_st.setPosition(poz8);
        //s.brat_dr.setPosition(poz9);



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


        telemetry.addData("incheietura out  ", s.incheieturaout.getPosition()); // punere bara
        telemetry.addData("brat dreapta out ", s.bratDreapta.getPosition()); // punere bara
        telemetry.addData("brat stanga out", s.bratStanga.getPosition()); // punere bara
//        telemetry.addData("gheara", s.ghearaIn.getPosition());
//        telemetry.addData("brat out", s.bratOut1.getPosition());
        //telemetry.addData("gheara out", s.ghearaOut.getPosition());
        telemetry.addData("incheietura", s.incheietura.getPosition()); //1
//        telemetry.addData("extendo", s.ext1.getPosition());
        //telemetry.addData("brat dr", s.brat_dr.getPosition()); //0.292
        //telemetry.addData("brat st", s.brat_st.getPosition()); //0.818
//        telemetry.addData("planul b", s.planulB.getPosition());
        // telemetry.addData("rotire", poz2); //0.54  //
        telemetry.update();



    }
}

