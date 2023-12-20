# Android-NEU-fingertip-Calender
Adopt the traditional MVC architecture design pattern and use JAVA language for development. The development content is a schedule management app "NEU-Calendar".
# 一、总体功能描述
采用传统的MVC架构设计模式，使用JAVA语言进行开发。开发内容是一款日程管理APP“东大日程（NEU-Calendar）”。软件的功能分为基础功能类和拓展功能类。

基本功能：①对日程的新建和修改；②根据日程内容对用户进行提醒。

拓展功能：①对于个人信息和个人喜好的设置；②学校课程表导入功能；③基于Web服务器的分享功能；④对于UI设计的优化。

完成基本要求中的功能：新建、修改日程；日程提醒。

完成扩展功能：个人信息、提醒方式与类型、导入学校课表、使用Web服务器实现一些功能。

完成界面布局要求：界面美观、操作便捷。

# 二、开发环境与运行环境

开发环境：Java1.8、Android Studio、Gradle 3.5.2。

硬件要求：移动端1G内存及以上；硬盘空间100M及以上

移动端要求：Android 11.0及以上

# 三、软件页面

# 1 注册界面

用户在使用本产品之前需要注册账号。因此用户如果是首次使用本App，需要手动注册账号。下面是进入注册界面：
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/1c6310e7-096c-4e8c-abba-ad726c6c57ca)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/de3b526f-dbb1-4276-bb43-e4ece36431cb)

带星星*的为必填项，当输入手机号码后，可以选择获取验证码。这里简单使用Web服务器。用户注册必须正确填写验证码（短信发送功能由榛子云提供，用户输入的手机号会发送给Web服务器，服务器生成验证码后调用榛子云SDK发送短信）。例如：(此处调试到目前还是会出现一些问题，可能由于是安卓模拟机的缘故，并不能收到正确的来自榛子云SDK发送的短信信息，因此这里采用安卓模拟机之间的通信来模拟验证码的发送)

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/d9e76259-1cdb-42d9-8166-58a95e09b9e9)


# 2 登陆界面(包含绑定QQ)

用户在使用本产品之前需要注册账号。因此用户如果是首次使用本App，需要手动注册账号。用户在登录过本app如果勾选记住密码选项，后续可以免密登录。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/ed73b021-8b61-4e65-8a07-867a244f282f)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/c384fdcf-12a5-4f4f-83fe-31b070dc9ee6)

点击绑定QQ界面：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/63665c40-570f-4cc1-aa53-a1eb013f62c6)

点击绑定即可将已经输入在登陆界面的账号与此QQ绑定，此举动会获取QQ的个人信息，简单运用了Web服务器。

# 3 日程界面
进入主页面，界面效果如下：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/e7088352-459f-480a-8a98-d098f91188e9)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/9daed5f6-f6ba-4f47-ba5a-609681b4c216)

用户点击上方的周日历，可以实现对于不同周的相应日期的日历切换。例如上图分别选择6.6和6.5。

# 3.1添加日程
点击右下方的“+”号按钮即可添加日程。弹窗提示用户输入日程标题：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/61ca2a84-bb63-4262-93dd-0444e64bfc9c)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/159c0188-131f-4939-8710-275a5c252a29)

由于现在是6.6，因此日程添加进入了6.6的时间里而不是6.5。

# 3.2 编辑（修改，删除）日程
可以修改创建日程时设置的所有属性。由于在创建日程中，用户提前定义好的仅仅有日程的名称，因此在任何日程被创建后，其它部分的数据都是系统自动初始化完成的。用户有需求需要进入日程进行修改。例如我们点击刚刚创建的日程：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/4473e37d-5bc8-4c0b-b62f-635ae604fb7f)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/e9b0d752-c220-420e-9d49-4c776ab587e8)

这里我们手动修改一下信息：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/b92c4073-0195-4368-86c0-96996fbcec7c)

# 3.3 日程提醒
再次修改日程提醒时间为系统当前时间，提醒界面如下图：
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/c5aba453-acb9-405e-9d74-00afc2459af1)

# 3.4 日程时间线可视化
日程支持时间线的可视化，点击“全部日程”。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/adba363e-5a81-4b7a-bf3c-b1c248cafe96)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/cebb547f-3cd7-4c8d-b313-028502ce4bfb)

创建了日程的时间线，让用户对自己创建的日程了如指掌。

# 4 自动导入教务处课程表
用户在首次进入日历页面时，可以选择登录东北大学教务处，并点击相应的导入课表选项，即可导入本学期的课表。在已经导入了课表后，则会显示对应的课表。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/4774aa33-0447-40f9-82d1-a0c1f474faa4)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/8658e415-3d0d-47dd-a0b9-547f245833c3)

点击对应的课程会显示课程的详细信息，支持对周数的切换，例如我们切换到15周：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/a401a8a5-6873-4146-a66a-8fb1e3416344)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/ac0c7691-d7be-4e0c-bb76-85e856a776eb)

课表的界面采用GridLayout布局。

# 5 个人页面
本app还集成开发了个人信息页面，具体界面如下：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/b13c7fa6-f503-4011-902d-505eb82a40aa)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/6a05ce32-c35b-4c12-8a15-a62ef80dabb5)

个人页面获取了QQ的基本信息(例如头像和昵称),顶部的任务状态栏会显示已经创建的日程数量，已完成日程数量和逾期日程数量。还有部分日程由于是今天创建的，因而只属于创建日程，不属于已完成状态或者逾期状态。点击最上方的任务状态栏，进入到个人信息的界面。个人信息主要存储了用户头像，用户昵称，独特的App_ID(采用MD5加密),绑定的QQ号，手机号和应急联系人号码。

日程统计为日程可视化的重要部分，主要包含日程的周视图，月视图和年视图，点击右上角可以进行视图的切换：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/7be7380d-f17b-444c-8502-af0a11643184)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/6fd52e4c-880f-4818-bc60-fce690a4dd01)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/81a683d7-6058-4c53-9afb-1995c17cb77a)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/09f3bccb-07cb-49e3-8183-13ca47da0412)

可以让用户对于自己的日程有更加直观的认识。
我的生日部分具有一定的新颖性，可以管理重要的生日日程，并且系统还会对这些生日有特殊的管理。例如我们创建东北大学的生日1923.04.26，系统计算得到今日距离东大的100周年生日还有324天，距离东大诞生已经过去了36201天。更为有趣的是，东北大学生肖属猪，星座为金牛座。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/63ca95ca-f63c-4df3-8e2d-0bda6fdd5bb7)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/a677722e-9625-4b3a-9635-903b66e12fd3)

然后是消息通知界面，此页面主要完成消息通知的设置功能与预览。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/7fbcb41f-4cb7-4a7b-819a-6a6fe77f8c3b)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/4b9802b1-2e7a-4ae3-9ac6-344d73d627de)

# 6 天气预报
本天气预报功能的集成开发主要依赖于中国天气网的天气数据。简单运用了Web服务器。首先需要获取城市的名称。这里我采用了两种方法：①是采用安卓自带的原生GPS定位系统；②是采用高德地图API定位系统。(后续会详细讲到)双重保险。在获取了用户的基本位置信息后，根据获取的经纬度来定位城市信息。通过中国天气网的天气数据进行可视化呈现在页面上。效果如图，和IPHONE自带的天气app做了一点比较，数据较为吻合：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/d002d4fb-b55d-4d55-b46f-17ded3a9774f)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/25821a4f-f190-4883-bcd7-e11f48c480c5)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/c5a24db4-49ae-41a3-a6b5-8ef858e61734)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/31348cbb-5ba4-4c94-a857-b97927eb9f57)

# 7 高德定位
本app运用了高德地图API，获取用户所在位置，简单运用了Web服务器。再注册完高德地图API相关的应用信息之后(key部分已经打码)，打开安卓手机权限，在用户同意定位后，便开始获取用户的地理位置的信息。这里有简单的效果图：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/7e41309c-205d-4297-9058-7474fda75091)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/9b4a086d-0c0d-4311-8ac6-14d23bc2fdea)

# 8 社区分享功能
在本功能中，使用到了LeanCloud的免费服务器。搭建了一个Web服务器，实现了东大社区的分享功能。

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/3d6f7840-8c3a-49df-8da9-6e0fd4d43ee6)

上图为存储在Web服务器上的数据集。下图为朋友圈分享功能的示例图：

![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/3e7ff820-9bde-4dcc-866a-60b135fb61c5)
![image](https://github.com/neuljh/Android-NEU-fingertip-Calender/assets/132900799/1dc30426-e236-4ce9-a3f6-8e198dd8e78c)

点击右上角的照相机logo，用户可以发表对应的内容到东大社区。整个过程中涉及联网操作，本地对LeanCloud服务器进行数据抓取和数据交互。




