package jvm.doing.com.compress;

public class CompressTest {

    private int intValue = 10;
    private boolean booValue = false;

    private void fun1() {
        intValue = 100;
    }

    private void funAdd() {
        intValue++;
        booValue = true;
    }

    private void fun2() {
        booValue = true;
    }


    private void run1() {
        int a = 0;
        int b = 3;
        System.out.print(a + b);
    }

    private void  run2() {
        final int a = 2;
        int b = 3;
        System.out.print(a + b);
    }
}
