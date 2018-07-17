package ming.com.avlearner.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ming on 17/8/18.
 */

public class CloseUtils {

    private CloseUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void closeQuietly(Closeable cloneable) {
        if (cloneable != null) {
            try {
                cloneable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
