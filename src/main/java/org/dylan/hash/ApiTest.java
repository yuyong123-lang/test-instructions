package org.dylan.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Dylan
 * @version 2024/6/11
 */
@Slf4j
public class ApiTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap01<>();
        map.put("01", "花花");
        map.put("02", "豆豆");
        log.info("碰撞前 key：{} value：{}", "01", map.get("01"));

        // 下标碰撞
        map.put("09", "蛋蛋");
        map.put("12", "苗苗");
        log.info("碰撞前 key：{} value：{}", "01", map.get("01"));
    }
}
