package com.tiger.useragent.temp;

import com.tiger.useragent.UserAgentParser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author allen
 * @date 2022-02-25
 * @since 1.0
 */
public class SingleParserTest {

    public static void main(String[] args) throws IOException {
        UserAgentParser parser = UserAgentParser.getInstance();

        String ua = "Mozilla/5.0 (Linux; U; Android 9; zh-CN; JAT-AL00 Build/HONORJAT-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 UWS/3.22.2.43 Mobile Safari/537.36 UCBS/3.22.2.43_220223200638 NebulaSDK/1.8.100112 Nebula AlipayDefined(nt:4G,ws:360|0|2.0) AliApp(AP/10.2.59.6000) AlipayClient/10.2.59.6000 Language/zh-Hans useStatusBar/true isConcaveScreen/true Region/CNAriver/1.0.0";
        System.out.println(parser.parse(ua));


        Pattern compile = Pattern.compile("(alipayclient)/(\\d+)\\.(\\d+)\\.(\\d+)(\\.(\\d+))?");
        Matcher matcher = compile.matcher(ua.toLowerCase());
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
