package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * done Given 一个LockerRobotManager
 * When 配置1个S号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置成功
 * <p>
 * done Given 一个LockerRobotManager
 * When 配置1个M号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置失败，提示请配置S号Locker
 * <p>
 * todo Given 一个LockerRobotManager
 * When 配置1个L号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置失败，提示请配置S号Locker
 * <p>
 * todo Given LockerRobotManager管理的Locker和Robot都未满，一个S号包
 * When 存包
 * Then 获得一张有效票据，包存到LockerRobotManager直接管理的Locker中
 * <p>
 * todo Given LockerRobotManager管理的Locker和Robot都未满，一个M号包
 * When 存包
 * Then 获得一张有效票据，包存到PrimaryLockerRobot管理的Locker中
 * <p>
 * todo Given LockerRobotManager管理的Locker和Robot都未满，一个L号包
 * When 存包
 * Then 获得一张有效票据，包存到SuperLockerRobot管理的Locker中
 * <p>
 * todo Given LockerRobotManager管理的Locker已满，Robot都未满，一个S号包
 * When 存包
 * Then 存包失败，提示S号Locker已满
 * <p>
 * todo Given LockerRobotManager管理的Locker和SuperLockerRobot未满，PrimaryLockerRobot已满，一个M号包
 * When 存包
 * Then 存包失败，提示M号Locker已满
 * <p>
 * todo Given LockerRobotManager管理的Locker和PrimaryLockerRobot未满，SuperLockerRobot已满，一个L号包
 * When 存包
 * Then 存包失败，提示L号Locker已满
 * <p>
 * todo Given 一张LockerRobotManager管理的S号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * todo Given 一张LockerRobotManager管理的M号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * todo Given 一张LockerRobotManager管理的L号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * todo Given 一张S号Locker的伪造票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * todo Given 一张M号Locker的伪造票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * todo Given 一张L号Locker的伪造票据
 * When 取包
 * Then 取包失败，提示非法票据
 */
public class LockerRobotManagerTest {
    private static final String CONFIG_FAILED_MSG = "请配置S号Locker";

    @Test
    void should_config_success_when_config_1_s_size_locker_given_1_locker_robot_manager() {
        Locker sSizeLocker = new Locker(5, BagSize.S);

        LockerRobotManager robot = new LockerRobotManager(
                sSizeLocker,
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
    }

    @Test
    void should_throw_config_failed_exception_when_config_1_m_size_locker_given_1_locker_robot_manager() {
        Locker mSizeLocker = new Locker(5, BagSize.M);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new LockerRobotManager(
                        mSizeLocker,
                        new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                        new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L)))));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }
}
