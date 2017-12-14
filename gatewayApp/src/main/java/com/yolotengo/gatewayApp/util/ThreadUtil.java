package com.yolotengo.gatewayApp.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dgallego on 26/10/2017.
 */
public class ThreadUtil {

    private ThreadUtil() {

    }


    private static final int MAX_THREAD = 50;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);

    public static ExecutorService getExecutorService() {
        return executorService;
    }

}
