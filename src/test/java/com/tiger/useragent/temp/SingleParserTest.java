package com.tiger.useragent.temp;

import com.tiger.useragent.UserAgentParser;

import java.io.IOException;

/**
 * @author allen
 * @date 2022-02-25
 * @since 1.0
 */
public class SingleParserTest {

    public static void main(String[] args) throws IOException {
        UserAgentParser parser = UserAgentParser.getInstance();

        System.out.println(parser.parse("Mozilla/5.0 (Linux; Android 11; M2007J22C Build/RP1A.200720.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045735 Mobile Safari/537.36 Dmall/5.2.8"));
    }
}
