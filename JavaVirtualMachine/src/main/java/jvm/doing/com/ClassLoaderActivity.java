package jvm.doing.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.jetbrains.anko.AnkoComponent;

import java.io.InputStream;

public class ClassLoaderActivity extends AppCompatActivity {

    private static final String TAG = "ClassLoaderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);

        ClassLoader classLoader = new ClassLoader() {


            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String className = name.substring(name.lastIndexOf(".") + 1);
//                    String className = name.replaceAll("\\.", "/");
                    String fileName = className + ".class";
                    InputStream inputStream = ClassLoaderActivity.class.getResourceAsStream(fileName);
                    if (inputStream == null) {
                        return super.loadClass(name);
                    }

                    byte[] buf = new byte[inputStream.available()];
                    inputStream.read(buf);
                    return defineClass(name, buf, 0, buf.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException();
                }

            }
        };

        Object obj1;
        try {
            obj1 = classLoader.loadClass(FinalClass.class.getName()).newInstance();

        } catch (Exception e) {
            obj1 = new FinalClass();
            e.printStackTrace();
        }
        Log.d(TAG, "Start");
        Log.d(TAG, obj1.getClass().getSimpleName());
        Log.d(TAG, obj1.getClass().getName());
        Log.d(TAG, obj1.getClass().getTypeName());
        Log.d(TAG, "Doing");
        Log.d(TAG, (obj1 instanceof FinalClass) + "");
        Log.d(TAG, String.valueOf(obj1.getClass().getClassLoader()));

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Log.d(TAG, String.valueOf(loader));

        Log.d(TAG, String.valueOf(Activity.class.getClassLoader()));
        Log.d(TAG, String.valueOf(String.class.getClassLoader()));
        Log.d(TAG, String.valueOf(AppCompatActivity.class.getClassLoader()));
        Log.d(TAG, String.valueOf(AnkoComponent.class.getClassLoader()));

    }
}
