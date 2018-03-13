package TradeDesk.utils;

import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptUtil {

    // MD5 hash algorithm
    public static int getHash(Object key) {
        byte[] hashedBytesArray = null;
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.ALGORITHM_MD5);
            byte[] keyBytesArray = key.toString().getBytes(Constants.CHARSET_UTF8);
            hashedBytesArray = md.digest(keyBytesArray);

        } catch(Exception ex) {
            Logger.getLogger(CryptUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Math.abs(MapUtil.byteArrayToInteger(hashedBytesArray));
    }
}
