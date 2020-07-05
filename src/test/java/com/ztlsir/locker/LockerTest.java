package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * done Given 一个未满的S号Locker，一个S号的包 When 存包 Then 存包成功，返回一张有效票据
 * todo Given 一个已满的S号Locker，一个S号的包 When 存包 Then 存包失败，提示Locker已满
 * done Given 一张S号Locker的有效票据 When 取包 Then 取包成功
 * todo Given 一张S号Locker的伪造票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张已取过包S号Locker的票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张M号Locker的有效票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张L号Locker的有效票据 When 取包 Then 取包失败，提示非法票据
 */
public class LockerTest {
    @Test
    void should_return_available_ticket_when_save_bag_given_one_available_s_locker_and_one_s_bag() {
        Locker locker = new Locker(5, BagSize.S);
        Bag bag = new Bag(BagSize.S);

        Ticket ticket = locker.saveBag(bag);

        assertNotNull(ticket);
    }

    @Test
    void should_take_bag_when_take_bag_given_one_useful_ticket() {
        Locker locker = new Locker(5, BagSize.S);
        Bag preSaveBag = new Bag(BagSize.S);
        Ticket ticket = locker.saveBag(preSaveBag);

        Bag bag = locker.takeBag(new Ticket(ticket.getSerialNo(), ticket.getBagSize()));

        assertEquals(preSaveBag, bag);
    }
}
