package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * done Given 一个SuperLockerRobot
 * When 配置1个L号Locker
 * Then 配置成功
 * <p>
 * todo Given 一个SuperLockerRobot
 * When 配置1个S号Locker
 * Then 配置失败，提示请配置L号Locker
 * <p>
 * todo Given 一个SuperLockerRobot
 * When 配置1个M号Locker
 * Then 配置失败，提示请配置L号Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，4
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，3
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：2，4
 * When 存包
 * Then 获得一张有效票据，包存到第2个Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，4
 * When 存包
 * Then 获得一张有效票据，包存到第2个Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：3，0
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，0
 * When 存包
 * Then 存包失败，提示Locker已满
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，一张包存在第一个Locker的有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * todo Given SuperLockerRobot管理2个L号Locker，一张包存在第二个Locker的有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * todo Given 一张L号Locker的伪造票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * todo Given 一张已取过包L号Locker的的票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * todo Given 一张S号Locker的有效票据
 * When 取包
 * Then 取包失败，提示仅支持包尺寸为L的票据
 * <p>
 * todo Given 一张M号Locker的有效票据
 * When 取包
 * Then 取包失败，提示仅支持包尺寸为L的票据
 */
public class SuperLockerRobotTest {
    @Test
    void should_config_success_when_config_1_l_size_locker_given_1_super_locker_robot() {
        Locker lSizeLocker = new Locker(5, BagSize.L);

        SuperLockerRobot robot = new SuperLockerRobot(Collections.singletonList(lSizeLocker));
    }
}
