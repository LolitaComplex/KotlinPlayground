package jvm.doing.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int _1MB = 1024 * 1024;
    private static final int _10MB = 10 * _1MB;
    private static final int _100MB = 100 * _1MB;
    private static final int _1GB = 1024 * _1MB;


    public static byte[] mAllocation1, mAllocation2,
            mAllocation3, mAllocation4;
    public static List<byte[]> mCache = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.mBtnAddMemory1).setOnClickListener(v -> {
//            mAllocation1 = new byte[_1GB];
        });

        findViewById(R.id.mBtnAddMemory2).setOnClickListener(v -> {
//            mAllocation2 = new byte[_1GB];
        });

        findViewById(R.id.mBtnAddMemory3).setOnClickListener(v -> {
            mAllocation3 = new byte[_100MB];
        });

        findViewById(R.id.mBtnAddMemory4).setOnClickListener(v -> {
            mAllocation4 = new byte[_100MB];
        });

        for (int i = 0; i < 30; i++) {
            mCache.add(new byte[_10MB]);
        }
    }
}
