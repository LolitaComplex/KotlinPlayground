package jvm.doing.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ClassLoaderKtActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ClassLoaderKtActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_loader)

        val classLoader = object: ClassLoader(){

            override fun loadClass(name: String): Class<*> {
                try {
                    val className = name.substring(name.lastIndexOf(".") + 1)
                    val fileName = "$className.class"
                    val inputStream = javaClass.getResourceAsStream(fileName)
                        ?: return super.loadClass(name)

                    val buf = ByteArray(inputStream.available())
                    inputStream.read(buf)
                    return defineClass(name, buf, 0, buf.size)
                } catch (e: Exception) {
                    throw ClassNotFoundException()
                }
            }
        }

        val obj1 = classLoader.loadClass(FinalClass().javaClass.name).newInstance()
//        val obj1 = FinalClass()

        Log.d(TAG, "Start")
        Log.d(TAG, obj1.javaClass.simpleName)
        Log.d(TAG, obj1.javaClass.name)
        Log.d(TAG, obj1.javaClass.typeName)
        Log.d(TAG, "Doing")
        Log.d(TAG, "${obj1 is FinalClass}")


    }
}
