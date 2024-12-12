# useragent-parser
这是一个 UserAgent 解析器的库，用于解析浏览器、操作系统、设备和 .net 版本等信息。此版本的解析器包含许多具有中国特征的特殊用户代理。

> 项目基于 [useragent-parser](https://github.com/zhaolihe/useragent-parser) 二次开发，主要为了持续更新设备信息。

## 特性
- 支持从[MobileModels](https://github.com/KHwang9883/MobileModels)中获取最新的手机品牌型号汇总，更新到解析库中。

# 快速开始
## 引入

### Gradle
```angular2html
implementation 'io.github.big-mouth-cn:useragent-parser:20241212'
```

### Maven
```angular2html
<dependency>
    <groupId>io.github.big-mouth-cn</groupId>
    <artifactId>useragent-parser</artifactId>
    <version>20241212</version>
</dependency>
```

# 使用示例
``` java
try {
  String uaString = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.55 Safari/534.3";
  UserAgentParser parser = UserAgentParser.getInstance();
  UserAgentInfo info = parser.getUserAgentInfo(uaString);
  System.out.println(info);
} catch (IOException e) {
  e.printStackTrace();
} 
```
输出解析结果:
```
{"os_type": 4, "os_name": "Windows", "os_detail": "Windows XP", "os_version": "XP", "browser_name": "Chrome", "browser_detail": "Chrome 6.0", "browser_version": "6.0", "device_brand": "PC", "device_name": "-", "device_type": "PC", "int_device_type": 4, "is_mobile": false, "net_type": "-", "int_net_type": 0}
```

# 问题
UserAgentInfo的描述是 `/main/resource/UserAgentInfo.avsc`

现在，这个库已经在使用中，如果在使用过程中有任何问题，请告诉我。我会在空闲时间进行优化。

# 鸣谢
- [useragent-parser](https://github.com/zhaolihe/useragent-parser)
- [MobileModels](https://github.com/KHwang9883/MobileModels)