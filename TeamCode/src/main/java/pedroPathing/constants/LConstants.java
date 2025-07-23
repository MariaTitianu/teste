package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = .001989436789;
        ThreeWheelConstants.strafeTicksToInches = .001989436789;
        ThreeWheelConstants.turnTicksToInches = -0.00182;
        ThreeWheelConstants.leftY = 1.77;
        ThreeWheelConstants.rightY = -1.77;
        ThreeWheelConstants.strafeX = -1.77;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "motorFL";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "motorFR";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "motorBL";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}




