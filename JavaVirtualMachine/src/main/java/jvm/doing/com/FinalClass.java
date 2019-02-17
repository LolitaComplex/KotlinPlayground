package jvm.doing.com;

import android.util.Log;

public final class FinalClass {

    private final String name = "Final name";

    public String getName() {
        return name;
    }

    public void run() {
        String name = new FinalClass().getName();
        Log.d("LOG", "run: " + name);
    }
}
