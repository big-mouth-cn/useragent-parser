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

        System.out.println(parser.parse("Mozilla/5.0 (Linux; Android 9; Redmi 6 Build/PPR1.180610.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/80.0.3987.99 Mobile Safari/537.36"));
    }
}
