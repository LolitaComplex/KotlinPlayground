package jvm.doing.com.compress;

public class Singleton {

    private static Singleton instance;
    private Singleton(){}

    public static Singleton getIntance(){
//        if (instance == null) {
//            synchronized (Singleton.class){
//                if (instance == null) {
                    instance = new Singleton();
//                }
//            }
//        }
        return instance;
    }
}
