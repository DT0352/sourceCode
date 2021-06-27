package com.itheima;

import org.junit.Test;

import java.util.*;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/6/14
 */
public class jdk8Test {
    @Test
    public  void test02() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Object[] array = list.toArray();
        // JDK8 返回 Integer[] 数组，JDK9+ 返回 Object[] 数组。
        System.out.println("array className ：" + array.getClass().getSimpleName());

        // 此处，在 JDK8 和 JDK9+ 表现不同，前者会报 ArrayStoreException 异常，后者不会。
        array[0] = new Object();
    }

    @Test
    public void test03() {
        ArrayList<Integer> integers = new ArrayList<>(10);
        integers.addAll(0,Arrays.asList(1,2,3));
        System.out.println(Arrays.toString(integers.toArray()));
        integers.removeAll(Arrays.asList(1));
        System.out.println(Arrays.toString(integers.toArray()));
        System.out.println(true == false );
        integers.iterator();
        new HashMap<>();
    }
}



