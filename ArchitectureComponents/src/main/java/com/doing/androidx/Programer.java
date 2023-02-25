package com.doing.androidx;

public class Programer {

    static String company = "CompanyA";

    static{
        System.out.println("staitc init");
    }

    String position;

    public Programer() {
        this.position = "engineer";
        char a = 'a';
        char[] array = new char[10];

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        thread.interrupt();
    }

    public void working() {
        System.out.println("coding...");
    }
}
