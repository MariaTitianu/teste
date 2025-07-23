package FunctiiSistem;
@com.acmerobotics.dashboard.config.Config

public class Config {
    public static double p = 50, i = 0, d = 2, setpoint = -120, armcoefficient=1, slidercoefficient = 1;
   // public static double pstatic = 0.006, istatic = 6e-9, dstatic = 0.006;
    public static double pslider = 0.007, islider = 0.0000005, dslider = 0.0008;
    public static double sasiu_p = 0.01, sasiu_i = 0, sasiu_d = 0, sasiu_f = 0.15 ;
    public static double sasiu_setpoint = 320;
    public static double sasiu_putere = 0.15;
    public static double camera_target_tolerance = 50, camera_outside_tolerance = 5;
    //public static double TOLERANTA = 4, INCREMENT = 0.001;

    public static double TOLERANTA = 6.35, INCREMENT = 0.0025;

    public static double turela_maxTime = 1350, turela_maxCorrection = 0.2, turela_p = 0.000092, turela_i = 0, turela_d = 0.0000005;
//    public static double pbrat = 0.008, ibrat = 0.00000055, dbrat = 0.00085;
//    public static double pretardat = 0.005, iretardat = 0.00001, dretardat = 0.002;
//    public static double pstaticaut = 0.0018, istaticaut = 0.0, dstaticaut = 0.00018;
//    public static double py = 0.0005, iy = 0, dy = 0.004, setpointY = 0;
//    public static double px = 0.001, ix = 0, dx = 0.006, setpointX = 0;
//    public static double sidewaysCalib = 45.8636, rotationCalib = 75.8;
//    public static double ps = 0.002,is = 0, ds = 0, setPointS = 0;
//    public static double toleranceX = 200, toleranceY = 200, toleranceRotatie = 2, toleranceXPlaca = 350, tolerantaYPlaca = 350, tolerance=200;
//    public static int targetVerifications = 15;
}
