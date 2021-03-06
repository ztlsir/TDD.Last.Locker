package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;
import com.ztlsir.locker.exception.IllegalTicketException;
import com.ztlsir.locker.exception.LockerFullException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.ztlsir.locker.fixture.LockerFixture.*;
import static com.ztlsir.locker.fixture.LockerFixture.ILLEGAL_TICKET_MSG;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * done Given 一个SuperLockerRobot
 * When 配置1个L号Locker
 * Then 配置成功
 * <p>
 * done Given 一个SuperLockerRobot
 * When 配置1个S号Locker
 * Then 配置失败，提示请配置L号Locker
 * <p>
 * done Given 一个SuperLockerRobot
 * When 配置1个M号Locker
 * Then 配置失败，提示请配置L号Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，4
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，3
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：2，4
 * When 存包
 * Then 获得一张有效票据，包存到第2个Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，4
 * When 存包
 * Then 获得一张有效票据，包存到第2个Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：3，0
 * When 存包
 * Then 获得一张有效票据，包存到第1个Locker
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，0
 * When 存包
 * Then 存包失败，提示Locker已满
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，一张包存在第一个Locker的有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * done Given SuperLockerRobot管理2个L号Locker，一张包存在第二个Locker的有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * done Given 一张L号Locker的伪造票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * done Given 一张已取过包L号Locker的的票据
 * When 取包
 * Then 取包失败，提示非法票据
 * <p>
 * done Given 一张S号Locker的有效票据
 * When 取包
 * Then 取包失败，提示仅支持包尺寸为L的票据
 * <p>
 * done Given 一张M号Locker的有效票据
 * When 取包
 * Then 取包失败，提示仅支持包尺寸为L的票据
 */
public class SuperLockerRobotTest {
    private static final String CONFIG_FAILED_MSG = "请配置L号Locker";
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持包尺寸为L的票据";

    @Test
    void should_config_success_when_config_1_l_size_locker_given_1_super_locker_robot() {
        Locker lSizeLocker = new Locker(5, BagSize.L);

        SuperLockerRobot robot = new SuperLockerRobot(Collections.singletonList(lSizeLocker));
    }

    @Test
    void should_throw_config_failed_exception_when_config_1_s_size_locker_given_1_super_locker_robot() {
        Locker sSizeLocker = new Locker(5, BagSize.S);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new SuperLockerRobot(Collections.singletonList(sSizeLocker)));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }

    @Test
    void should_throw_config_failed_exception_when_config_1_m_size_locker_given_1_super_locker_robot() {
        Locker mSizeLocker = new Locker(5, BagSize.M);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new SuperLockerRobot(Collections.singletonList(mSizeLocker)));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }

    @Test
    void should_save_in_1st_locker_when_save_bag_given_super_manage_2_l_size_available_lockers_and_each_lockers_remain_is_equals() {
        Locker firstLocker = createLSizeLocker(5, 4);
        SuperLockerRobot robot = new SuperLockerRobot(asList(firstLocker, createLSizeLocker(5, 4)));
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = firstLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_1st_locker_when_save_bag_given_super_manage_2_l_size_available_lockers_and_1st_locker_more_than_2nd_locker() {
        Locker firstLocker = createLSizeLocker(5, 4);
        SuperLockerRobot robot = new SuperLockerRobot(asList(firstLocker, createLSizeLocker(5, 3)));
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = firstLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_2nd_locker_when_save_bag_given_super_manage_2_l_size_available_lockers_and_2nd_locker_more_than_1st_locker() {
        Locker secondLocker = createLSizeLocker(5, 4);
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(5, 2), secondLocker));
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = secondLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_2nd_locker_when_save_bag_given_super_manage_2_l_size_lockers_and_1st_locker_is_full_and_2nd_is_available() {
        Locker secondLocker = createLSizeLocker(5, 4);
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(5, 0), secondLocker));
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = secondLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_1st_locker_when_save_bag_given_super_manage_2_l_size_lockers_and_2nd_locker_is_full_and_1st_is_available() {
        Locker firstLocker = createLSizeLocker(5, 3);
        SuperLockerRobot robot = new SuperLockerRobot(asList(firstLocker, createLSizeLocker(5, 0)));
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = robot.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = firstLocker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_throw_locker_full_exception_when_save_bag_given_super_manage_2_full_l_size_lockers() {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(5, 0), createLSizeLocker(5, 0)));
        Bag preSaveBag = new Bag(BagSize.L);

        LockerFullException exception = assertThrows(LockerFullException.class, () -> robot.saveBag(preSaveBag));
        assertEquals(LOCKER_FULL_MSG, exception.getMessage());
    }

    @Test
    void should_take_bag_when_take_bag_given_super_manage_two_locker_and_one_useful_ticket_that_bag_is_saved_in_1st_locker() {
        verifyTakeBag(5);
    }

    @Test
    void should_take_bag_when_take_bag_given_super_manage_two_locker_and_one_useful_ticket_that_bag_is_saved_in_2nd_locker() {
        verifyTakeBag(3);
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_one_l_size_fake_ticket() {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(4, 3), createLSizeLocker(6, 4)));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> robot.takeBag(new Ticket(FAKE_TICKET, BagSize.L)));
        assertEquals(ILLEGAL_TICKET_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_one_had_taken_ticket() {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(6, 4), createLSizeLocker(6, 4)));
        Bag preSaveBag = new Bag(BagSize.L);
        Ticket ticket = robot.saveBag(preSaveBag);
        robot.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize()));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> robot.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize())));
        assertEquals(ILLEGAL_TICKET_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_one_s_size_fake_ticket() {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(4, 3), createLSizeLocker(6, 4)));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> robot.takeBag(new Ticket(Ticket.createId(), BagSize.S)));
        assertEquals(BAG_SIZE_MISMATCHING_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_one_m_size_fake_ticket() {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(4, 3), createLSizeLocker(6, 4)));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> robot.takeBag(new Ticket(Ticket.createId(), BagSize.M)));
        assertEquals(BAG_SIZE_MISMATCHING_MSG, exception.getMessage());
    }

    private void verifyTakeBag(int firstLockerRemain) {
        SuperLockerRobot robot = new SuperLockerRobot(asList(createLSizeLocker(6, firstLockerRemain), createLSizeLocker(6, 4)));
        Bag preSaveBag = new Bag(BagSize.L);
        Ticket ticket = robot.saveBag(preSaveBag);

        Bag bag = robot.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize()));

        assertEquals(preSaveBag, bag);
    }
}
