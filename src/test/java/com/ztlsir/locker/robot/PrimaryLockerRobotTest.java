package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import org.junit.jupiter.api.Test;

/**
 * done Given 一个PrimaryLockerRobot When 配置1个M号Locker Then 配置成功
 * todo Given 一个PrimaryLockerRobot When 配置1个S号Locker Then 配置失败，提示请配置M号Locker
 * todo Given 一个PrimaryLockerRobot When 配置1个L号Locker Then 配置失败，提示请配置M号Locker
 * todo Given PrimaryLockerRobot管理2个M号未满的Locker When 存包 Then 获得一张有效票据，包存到第1个Locker
 * todo Given PrimaryLockerRobot管理2个M号已满的Locker，第2个Locker未满 When 存包 Then 获得一张有效票据，包存到第2个Locker
 * todo Given PrimaryLockerRobot管理2个M号Locker，第1个Locker未满，第2个Locker已满 When 存包 Then 获得一张有效票据，包存到第1个Locker
 * todo Given PrimaryLockerRobot管理2个M号已满的Locker When 存包 Then 存包失败，提示Locker已满
 * todo Given 一张M号Locker的有效票据 When 取包 Then 取包成功
 * todo Given 一张M号Locker的伪造票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张已取过包M号Locker的的票据 When 取包 Then 取包失败，提示非法票据
 * todo Given 一张S号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
 * todo Given 一张L号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
 */
public class PrimaryLockerRobotTest {
    @Test
    void should_config_success_when_config_1_m_size_locker_given_1_primary_locker_robot() {
        Locker mSizeLocker = new Locker(5, BagSize.M);

        PrimaryLockerRobot robot = new PrimaryLockerRobot(mSizeLocker);
    }
}
