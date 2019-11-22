package com.alkhalilbg.moviesapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIo;

    public AppExecutors(Executor diskIo) {
        this.diskIo = diskIo;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }

        return sInstance;
    }

    public Executor diskIo() {
        return diskIo;
    }
}
