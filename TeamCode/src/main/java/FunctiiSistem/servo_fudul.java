package FunctiiSistem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;;
@TeleOp
public class servo_fudul extends OpMode {
    public ServoImplEx ext1, ext2,brat_st,brat_dr;

    double poz1 = p.ext_inchis, poz2 = 0, poz3 = 0.85, poz4 = 0.5,  poz6= 0.5, poz7 = 0.4, poz9=0.5;
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
        if (gamepad2.x && poz1<1){
            poz1+=0.009;
        }
        if (gamepad2.b && poz1>0){
            poz1-=0.009;
        }
        s.ext1.setPosition(poz1);
        s.ext2.setPosition(poz1);
//        if (gamepad2.y && poz4>0){
//            poz4-=0.001;
//        }
//        if (gamepad2.a && poz4<1){
//            poz4+=0.001;
//        }
//        s.bratOut2.setPosition(poz4);
//        s.bratOut1.setPosition(poz4);

        if (gamepad2.y && poz4>0){
            poz4-=0.009;
        }
        if (gamepad2.a && poz4 <1){
            poz4+=0.009;
        }
        s.rotire.setPosition(poz4);
//
//        if (gamepad2.dpad_right && poz2>0){
//            poz2-=0.001;
//        }
//        if (gamepad2.dpad_left && poz2<1){
//            poz2+=0.001;
//        }
//        s.incheietura.setPosition(poz2);
//
        if (gamepad2.dpad_down && poz7>0){
           // poz6-=0.009;
            poz7-=0.009;
        }
        if (gamepad2.dpad_up && poz7<1){
           // poz6+=0.009;
            poz7+=0.009;
        }
        //s.brat_dr.setPosition(poz7);
        s.turela.setPosition(poz6);

//        if (gamepad2.right_bumper && poz3>0){
//            poz3-=0.001;
//        }
//        if (gamepad2.left_bumper && poz3<1){
//            poz3+=0.001;
//        }
//        s.incheietura_out.setPosition(poz3);
//
        if (gamepad2.right_trigger>0 && poz6>0){
            poz6-=0.009;
            //poz7+=0.009;
        }
        if (gamepad2.left_trigger>0 && poz6<1){
            poz6+=0.009;
           // poz7-=0.009;
        }
        //s.brat_st.setPosition(poz6);
        s.bratIN.setPosition(poz7);

        if (gamepad1.x && poz2>0){
            poz2-=0.01;
        }
        if (gamepad1.b && poz2<1){
            poz2+=0.01;
        }
        //s.rotire.setPosition(poz2);
        if (gamepad2.right_bumper && poz9>0){
            poz9-=0.009;

        }
        if (gamepad2.left_bumper && poz9<1){
            poz9+=0.009;

        }
        s.ghearaIn.setPosition(poz9);



     //   s.ghearaOut.setPosition(p.gheara_out_deschis);

       // s.bratIn2.setPosition(poz2);  //
//        s.bratOut2.setPosition(poz);
        if (gamepad2.dpad_left && poz3<1){
            poz3+=0.009;
        }
        if (gamepad2.dpad_right && poz3>0){
            poz3-=0.009;
        }
        s.incheietura.setPosition(poz3);
//        if (gamepad2.touchpad) s.ghearaOut.setPosition(p.poz_out_inchis);
////

        // telemetry.addData("stanga", poz2);
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        telemetry.addData("Ext", poz1);

        telemetry.addData("incheietura ", s.incheietura.getPosition()); //0.33  //0.668, 0.633
        telemetry.addData("brat_IN", s.bratIN.getPosition());
        telemetry.addData("turela", s.turela.getPosition());
        telemetry.addData("gheara", s.ghearaIn.getPosition());
//        telemetry.addData("gheara", s.ghearaIn.getPosition());
//        telemetry.addData("brat out", s.bratOut1.getPosition());
//        telemetry.addData("gheara out", s.ghearaOut.getPosition());
//        telemetry.addData("incheietura out", s.incheietura_out.getPosition());
//        telemetry.addData("extendo", s.ext1.getPosition());
        telemetry.addData("rotire", s.rotire.getPosition());
//        telemetry.addData("planul b", s.planulB.getPosition());
       // telemetry.addData("rotire", poz2); //0.54  //
        telemetry.update();
    }
}

