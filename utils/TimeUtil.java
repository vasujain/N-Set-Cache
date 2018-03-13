package TradeDesk.utils;

import java.util.Date;

public class TimeUtil {

    public static long getCurrentTimestamp() {
        Date dt = new Date(System.currentTimeMillis());
        return dt.getTime();
    }
}
