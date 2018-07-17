package ming.com.avlearner.utils;

import android.util.Log;

public class LogUtils {
    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void debug(String log) {
        Log.d("----------", log);
    }

    public static void info(String log) {
        Log.d("----------", log);
    }

    public static void error(String log) {
        Log.d("----------", log);
    }

}
