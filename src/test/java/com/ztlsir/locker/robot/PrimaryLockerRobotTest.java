package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.ztlsir.locker.fixture.LockerFixture.createMSizeLocker;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * done Given 一个PrimaryLockerRobot When 配置1个M号Locker Then 配置成功
 * done Given 一个PrimaryLockerRobot When 配置1个S号Locker Then 配置失败，提示请配置M号Locker
 * done Given 一个PrimaryLockerRobot When 配置1个L号Locker Then 配置失败，提示请配置M号Locker
 * done Given PrimaryLockerRobot管理2个M号未满的Locker When 存包 Then 获得一张有效票据，包存到第1个Locker
 * done Given PrimaryLockerRobot管理2个M号已满的Locker，第2个Locker未满 When 存包 Then 获得一张有效票据，包存到第2个Locker
 * done Given PrimaryLockerRobot管理2个M号Locker，第1个Locker未满，第2个Locker已满 When 存包 Then 获得一张有效票据，包存到第1个Locker
 * todo Given PrimaryLockerRobot管理2个M号已满的Locker When 存包 Then 存包失败，提示Locker已满
 * todo Given 一张M号Locker的有效票据 When 取包 Then 取包成功
 * todo Given 一张M号Locker的伪造票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张已取过包M号Locker的的票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张S号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
 * todo Given 一张L号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
 */
class PrimaryLockerRobotTest {
    private static final String CONFIG_FAILED_MSG = "请配置M号Locker";

    @Test
    void should_config_success_when_config_1_m_size_locker_given_1_primary_locker_robot() {
        Locker mSizeLocker = new Locker(5, BagSize.M);

        PrimaryLockerRobot robot = new PrimaryLockerRobot(Collections.singletonList(mSizeLocker));
    }

    @Test
    void should_throw_config_failed_exception_when_config_1_s_size_locker_given_1_primary_locker_robot() {
        Locker sSizeLocker = new Locker(5, BagSize.S);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new PrimaryLockerRobot(Collections.singletonList(sSizeLocker)));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }

    @Test
    void should_throw_config_failed_exception_when_config_1_l_size_locker_given_1_primary_locker_robot() {
        Locker lSizeLocker = new Locker(5, BagSize.L);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new PrimaryLockerRobot(Collections.singletonList(lSizeLocker)));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }

    @Test
    void should_save_in_1st_locker_when_save_bag_given_primary_manage_2_m_size_available_lockers() {
        Locker firstLocker = new Locker(5, BagSize.M);
        PrimaryLockerRobot robot = new PrimaryLockerRobot(asList(firstLocker, new Locker(6, BagSize.M)));
        Bag preSaveBag = new Bag(BagSize.M);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = firstLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_2nd_locker_when_save_bag_given_primary_manage_2_m_size_lockers_and_1st_is_full_and_2nd_is_available() {
        Locker secondLocker = new Locker(5, BagSize.M);
        PrimaryLockerRobot robot = new PrimaryLockerRobot(asList(createMSizeLocker(6, 0), secondLocker));
        Bag preSaveBag = new Bag(BagSize.M);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = secondLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_1st_locker_when_save_bag_given_primary_manage_2_m_size_lockers_and_1st_is_available_and_2nd_is_full() {
        Locker firstLocker = new Locker(5, BagSize.M);
        PrimaryLockerRobot robot = new PrimaryLockerRobot(asList(firstLocker, createMSizeLocker(6, 0)));
        Bag preSaveBag = new Bag(BagSize.M);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = firstLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }
}
