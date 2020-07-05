package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.IllegalTicketException;
import com.ztlsir.locker.exception.LockerFullException;
import org.junit.jupiter.api.Test;

import static com.ztlsir.locker.fixture.LockerFixture.createSSizeLocker;
import static org.junit.jupiter.api.Assertions.*;

/**
 * done Given 一个未满的S号Locker，一个S号的包 When 存包 Then 存包成功，返回一张有效票据
 * done Given 一张S号Locker的有效票据,一个S号Locker When 取包 Then 取包成功
 * done Given 一个已满的S号Locker，一个S号的包 When 存包 Then 存包失败，提示Locker已满
 * done Given 一张S号Locker的伪造票据,一个S号Locker When 取包 Then 取包失败，提示非法票据
 * done Given 一张已取过包S号Locker的票据,一个S号Locker When 取包 Then 取包失败，提示非法票据
 * done Given 一张M号Locker的有效票据,一个S号Locker When 取包 Then 取包失败，提示仅支持S号大小的票据
 * done Given 一张L号Locker的有效票据,一个S号Locker When 取包 Then 取包失败，提示仅支持S号大小的票据
 */
class LockerTest {

    private static final String LOCKER_FULL_MSG = "Locker已满";
    private static final String ILLEGAL_TICKET_MSG = "非法票据";
    private static final String FAKE_TICKET = "fake_ticket";
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持S号大小的票据";

    @Test
    void should_return_available_ticket_when_save_bag_given_one_available_s_size_locker_and_one_s_bag() {
        Locker locker = createSSizeLocker(5, 5);
        Bag preSaveBag = new Bag(BagSize.S);

        Ticket ticket = locker.saveBag(preSaveBag);

        assertNotNull(ticket);
    }

    @Test
    void should_take_bag_when_take_bag_given_one_useful_ticket_and_one_s_size_locker() {
        Locker locker = createSSizeLocker(5, 5);
        Bag preSaveBag = new Bag(BagSize.S);
        Ticket ticket = locker.saveBag(preSaveBag);

        Bag bag = locker.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize()));

        assertEquals(preSaveBag, bag);
    }

    @Test
    void should_throw_locker_full_exception_when_save_bag_given_s_size_locker_is_full() {
        Locker locker = createSSizeLocker(5, 0);
        Bag preSaveBag = new Bag(BagSize.S);

        LockerFullException exception = assertThrows(
                LockerFullException.class,
                () -> locker.saveBag(preSaveBag));
        assertEquals(LOCKER_FULL_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_s_size_locker_fake_ticket_and_one_s_size_locker() {
        Locker locker = createSSizeLocker(5, 5);

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> locker.takeBag(new Ticket(FAKE_TICKET, BagSize.S)));
        assertEquals(ILLEGAL_TICKET_MSG, exception.getMessage());
    }

    @Test
    void should_throw_ilLegal_ticket_exception_when_take_bag_given_has_taken_ticket_and_one_s_size_locker() {
        Locker locker = createSSizeLocker(5, 5);
        Bag preSaveBag = new Bag(BagSize.S);
        Ticket ticket = locker.saveBag(preSaveBag);
        locker.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize()));

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> locker.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize())));
        assertEquals(ILLEGAL_TICKET_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_m_size_locker_ticket_and_one_s_size_locker() {
        Locker locker = createSSizeLocker(6, 6);

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> locker.takeBag(new Ticket(Ticket.createId(), BagSize.M)));
        assertEquals(BAG_SIZE_MISMATCHING_MSG, exception.getMessage());
    }

    @Test
    void should_throw_illegal_ticket_exception_when_take_bag_given_l_size_locker_ticket_and_one_s_size_locker() {
        Locker locker = createSSizeLocker(6, 6);

        IllegalTicketException exception = assertThrows(
                IllegalTicketException.class,
                () -> locker.takeBag(new Ticket(Ticket.createId(), BagSize.L)));
        assertEquals(BAG_SIZE_MISMATCHING_MSG, exception.getMessage());
    }
}
