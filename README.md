# TDD.Last.Locker
TDD 结业考试作业

## Tasking
### Locker
```
Given 一个未满的S号Locker，一个S号的包 When 存包 Then 存包成功，返回一张有效票据
Given 一张S号Locker的有效票据,一个S号Locker When 取包 Then 取包成功
Given 一个已满的S号Locker，一个S号的包 When 存包 Then 存包失败，提示Locker已满
Given 一张S号Locker的伪造票据,一个S号Locker When 取包 Then 取包失败，提示非法票据
Given 一张已取过包S号Locker的票据,一个S号Locker When 取包 Then 取包失败，提示非法票据
Given 一张M号Locker的有效票据,一个S号Locker When 取包 Then 取包失败，提示仅支持包尺寸为S的票据
Given 一张L号Locker的有效票据,一个S号Locker When 取包 Then 取包失败，提示仅支持包尺寸为S的票据
```
### PrimaryLockerRobot
```
Given 一个PrimaryLockerRobot When 配置1个M号Locker Then 配置成功
Given 一个PrimaryLockerRobot When 配置1个S号Locker Then 配置失败，提示请配置M号Locker
Given 一个PrimaryLockerRobot When 配置1个L号Locker Then 配置失败，提示请配置M号Locker
Given PrimaryLockerRobot管理2个M号未满的Locker When 存包 Then 获得一张有效票据，包存到第1个Locker
Given PrimaryLockerRobot管理2个M号已满的Locker，第2个Locker未满 When 存包 Then 获得一张有效票据，包存到第2个Locker
Given PrimaryLockerRobot管理2个M号Locker，第1个Locker未满，第2个Locker已满 When 存包 Then 获得一张有效票据，包存到第1个Locker
Given PrimaryLockerRobot管理2个M号已满的Locker When 存包 Then 存包失败，提示Locker已满
Given 一张M号Locker的有效票据 When 取包 Then 取包成功
Given 一张M号Locker的伪造票据 When 取包 Then 取包失败，提示非法票据
Given 一张已取过包M号Locker的的票据 When 取包 Then 取包失败，提示非法票据
Given 一张S号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
Given 一张L号Locker的有效票据 When 取包 Then 取包失败，提示仅支持包尺寸为M的票据
```
### SuperLockerRobot
```
Given 一个SuperLockerRobot
When 配置1个L号Locker
Then 配置成功

Given 一个SuperLockerRobot
When 配置1个S号Locker
Then 配置失败，提示请配置L号Locker

Given 一个SuperLockerRobot
When 配置1个M号Locker
Then 配置失败，提示请配置L号Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，4
When 存包
Then 获得一张有效票据，包存到第1个Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：4，3
When 存包
Then 获得一张有效票据，包存到第1个Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：2，4
When 存包
Then 获得一张有效票据，包存到第2个Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，4
When 存包
Then 获得一张有效票据，包存到第2个Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：3，0
When 存包
Then 获得一张有效票据，包存到第1个Locker

Given SuperLockerRobot管理2个L号Locker，容量分别为：5，5，余量分别为：0，0
When 存包
Then 存包失败，提示Locker已满

Given 一张L号Locker的有效票据
When 取包
Then 取包成功

Given 一张L号Locker的伪造票据
When 取包
Then 取包失败，提示非法票据

Given 一张已取过包L号Locker的的票据
When 取包
Then 取包失败，提示非法票据

Given 一张S号Locker的有效票据
When 取包
Then 取包失败，提示仅支持包尺寸为L的票据

Given 一张M号Locker的有效票据
When 取包
Then 取包失败，提示仅支持包尺寸为L的票据
```
### LockerRobotManager
```
Given 一个LockerRobotManager
When 配置1个S号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
Then 配置成功

Given 一个LockerRobotManager
When 配置1个M号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
Then 配置失败，提示请配置S号Locker

Given 一个LockerRobotManager
When 配置1个L号Locker，配置1个PrimaryLockerRobot，配置1个SuperLockerRobot
Then 配置失败，提示请配置S号Locker

Given LockerRobotManager管理的Locker和Robot都未满，一个S号包
When 存包
Then 获得一张有效票据，包存到LockerRobotManager直接管理的Locker中

Given LockerRobotManager管理的Locker和Robot都未满，一个M号包
When 存包
Then 获得一张有效票据，包存到PrimaryLockerRobot管理的Locker中

Given LockerRobotManager管理的Locker和Robot都未满，一个L号包
When 存包
Then 获得一张有效票据，包存到SuperLockerRobot管理的Locker中

Given LockerRobotManager管理的Locker已满，Robot都未满，一个S号包
When 存包
Then 存包失败，提示S号Locker已满

Given LockerRobotManager管理的Locker和SuperLockerRobot未满，PrimaryLockerRobot已满，一个M号包
When 存包
Then 存包失败，提示M号Locker已满

Given LockerRobotManager管理的Locker和PrimaryLockerRobot未满，SuperLockerRobot已满，一个L号包
When 存包
Then 存包失败，提示L号Locker已满

Given 一张LockerRobotManager管理的S号Locker有效票据
When 取包
Then 取包成功

Given 一张LockerRobotManager管理的M号Locker有效票据
When 取包
Then 取包成功

Given 一张LockerRobotManager管理的L号Locker有效票据
When 取包
Then 取包成功

Given 一张S号Locker的伪造票据
When 取包
Then 取包失败，提示非法票据

Given 一张M号Locker的伪造票据
When 取包
Then 取包失败，提示非法票据

Given 一张L号Locker的伪造票据
When 取包
Then 取包失败，提示非法票据
```