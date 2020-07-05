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
import static org.junit.jupiter.api.Assertions.*;

/**
 * done Given 一个LockerRobotManager
 * When 配置1个S号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置成功
 * <p>
 * done Given 一个LockerRobotManager
 * When 配置1个M号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置失败，提示请配置S号Locker
 * <p>
 * done Given 一个LockerRobotManager
 * When 配置1个L号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
 * Then 配置失败，提示请配置S号Locker
 * <p>
 * done Given LockerRobotManager管理的Locker和Robot都未满，一个S号包
 * When 存包
 * Then 获得一张有效票据，包存到LockerRobotManager直接管理的Locker中
 * <p>
 * done Given LockerRobotManager管理的Locker和Robot都未满，一个M号包
 * When 存包
 * Then 获得一张有效票据，包存到PrimaryLockerRobot管理的Locker中
 * <p>
 * done Given LockerRobotManager管理的Locker和Robot都未满，一个L号包
 * When 存包
 * Then 获得一张有效票据，包存到SuperLockerRobot管理的Locker中
 * <p>
 * done Given LockerRobotManager管理的Locker已满，Robot都未满，一个S号包
 * When 存包
 * Then 存包失败，提示S号Locker已满
 * <p>
 * done Given LockerRobotManager管理的Locker和SuperLockerRobot未满，PrimaryLockerRobot已满，一个M号包
 * When 存包
 * Then 存包失败，提示M号Locker已满
 * <p>
 * done Given LockerRobotManager管理的Locker和PrimaryLockerRobot未满，SuperLockerRobot已满，一个L号包
 * When 存包
 * Then 存包失败，提示L号Locker已满
 * <p>
 * done Given 一张LockerRobotManager管理的S号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * done Given 一张LockerRobotManager管理的M号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * done Given 一张LockerRobotManager管理的L号Locker有效票据
 * When 取包
 * Then 取包成功
 * <p>
 * done Given 一张S号Locker的伪造票据
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

        LockerRobotManager manager = new LockerRobotManager(
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

    @Test
    void should_throw_config_failed_exception_when_config_1_L_size_locker_given_1_locker_robot_manager() {
        Locker lSizeLocker = new Locker(5, BagSize.L);

        ConfigFailedException exception = assertThrows(
                ConfigFailedException.class,
                () -> new LockerRobotManager(
                        lSizeLocker,
                        new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                        new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L)))));
        assertEquals(CONFIG_FAILED_MSG, exception.getMessage());
    }

    @Test
    void should_save_in_locker_when_save_bag_given_locker_robot_manager_manage_available_locker_and_robot_and_one_s_size_bag() {
        Locker locker = createSSizeLocker(5, 4);
        LockerRobotManager manager = new LockerRobotManager(
                locker,
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.S);

        Ticket ticket = manager.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = locker.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_primary_when_save_bag_given_locker_robot_manager_manage_available_locker_and_robot_and_one_m_size_bag() {
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M)));
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                primaryLockerRobot,
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.M);

        Ticket ticket = manager.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = primaryLockerRobot.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_save_in_super_when_save_bag_given_locker_robot_manager_manage_available_locker_and_robot_and_one_l_size_bag() {
        SuperLockerRobot superLockerRobot = new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L)));
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                superLockerRobot);
        Bag preSaveBag = new Bag(BagSize.L);

        Ticket ticket = manager.saveBag(preSaveBag);

        assertNotNull(ticket);
        Bag bag = superLockerRobot.takeBag(ticket);
        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_throw_locker_full_exception_when_save_bag_given_locker_robot_manager_manage_full_locker_and_available_robot_and_one_s_size_bag() {
        Locker locker = createSSizeLocker(5, 0);
        LockerRobotManager manager = new LockerRobotManager(
                locker,
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.S);

        LockerFullException exception = assertThrows(LockerFullException.class, () -> manager.saveBag(preSaveBag));
        assertEquals(LOCKER_FULL_MSG, exception.getMessage());
    }

    @Test
    void should_throw_locker_full_exception_when_save_bag_given_locker_robot_manager_manage_available_locker_and_super_and_full_primary_and_one_m_size_bag() {
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot(Collections.singletonList(createMSizeLocker(5, 0)));
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                primaryLockerRobot,
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.M);

        LockerFullException exception = assertThrows(LockerFullException.class, () -> manager.saveBag(preSaveBag));
        assertEquals(LOCKER_FULL_MSG, exception.getMessage());
    }

    @Test
    void should_throw_locker_full_exception_when_save_bag_given_locker_robot_manager_manage_available_locker_and_primary_and_full_super_and_one_m_size_bag() {
        SuperLockerRobot superLockerRobot = new SuperLockerRobot(Collections.singletonList(createLSizeLocker(5, 0)));
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                superLockerRobot);
        Bag preSaveBag = new Bag(BagSize.L);

        LockerFullException exception = assertThrows(LockerFullException.class, () -> manager.saveBag(preSaveBag));
        assertEquals(LOCKER_FULL_MSG, exception.getMessage());
    }

    @Test
    void should_take_bag_when_take_bag_given_one_useful_s_size_ticket() {
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.S);
        Ticket ticket = manager.saveBag(preSaveBag);

        Bag bag = manager.takeBag(ticket);

        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_take_bag_when_take_bag_given_one_useful_m_size_ticket() {
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.M);
        Ticket ticket = manager.saveBag(preSaveBag);

        Bag bag = manager.takeBag(ticket);

        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_take_bag_when_take_bag_given_one_useful_l_size_ticket() {
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));
        Bag preSaveBag = new Bag(BagSize.L);
        Ticket ticket = manager.saveBag(preSaveBag);

        Bag bag = manager.takeBag(ticket);

        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_one_s_size_fake_ticket() {
        LockerRobotManager manager = new LockerRobotManager(
                createSSizeLocker(5, 4),
                new PrimaryLockerRobot(Collections.singletonList(new Locker(5, BagSize.M))),
                new SuperLockerRobot(Collections.singletonList(new Locker(5, BagSize.L))));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> manager.takeBag(new Ticket(FAKE_TICKET, BagSize.L)));
        assertEquals(ILLEGAL_TICKET_MSG, exception.getMessage());
    }
}
