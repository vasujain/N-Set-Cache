package TradeDesk.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MapUtil {
    // Maps ?byte Array to integer
    public static int byteArrayToInteger(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
}
