import com.fuvidy.async.Application;
import com.fuvidy.async.service.DemoService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/8/31
 */
@SpringBootTest(classes = Application.class)
public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);
    @Autowired
    DemoService service;

    @Test
    void test1() {
        logger.info("开始执行同步任务");
        long begin = System.currentTimeMillis();
        service.execute01();
        service.execute02();

        logger.info("执行结束,耗时{}毫秒", System.currentTimeMillis() - begin);
    }

    @Test
    void test2() {
        logger.info("开始执行异步任务");
        long begin = System.currentTimeMillis();
        service.execute03();
        service.execute04();
        logger.info("执行结束,耗时{}毫秒", System.currentTimeMillis() - begin);
    }

    @Test
    void test3() throws ExecutionException, InterruptedException {
        logger.info("开始执行异步任务");
        long begin = System.currentTimeMillis();
        Future<Integer> a1 = service.execute03();
        Future<Integer> a2 = service.execute04();
        System.out.println(a1.get());
        System.out.println(a2.get());
        logger.info("执行结束,耗时{}毫秒", System.currentTimeMillis() - begin);
    }


}
