package com.fuvidy.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/8/31
 */
@Service
public class DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    public Integer execute01() {
        logger.info("[execute01]");
        sleep(5);
        return 1;

    }

    public Integer execute02() {
        logger.info("[execute02]");
        sleep(7);
        return 2;

    }

    @Async
    public Future<Integer> execute03() {
        logger.info("[execute01]");
        sleep(5);
        return AsyncResult.forValue(1);

    }

    @Async
    public Future<Integer> execute04() {
        logger.info("[execute02]");
        sleep(7);
        return AsyncResult.forValue(2);

    }

    private static void sleep(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
