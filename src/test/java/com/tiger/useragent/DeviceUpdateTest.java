package com.tiger.useragent;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>自动更新设备型号脚本</p>
 *
 * <p>使用说明：</p>
 * <ul>
 *     <li>首先克隆 Git （https://github.com/KHwang9883/MobileModels）到本地，已克隆的更新到最新版本；</li>
 *     <li>修改 {@link #brands} 为克隆后项目的 /brands 目录；</li>
 *     <li>执行 {@link #main(String[])} 方法；</li>
 *     <li>将生成的文件 src/test/resources/{yyyyMMdd}_DeviceDictionary_Auto.txt 复制到 src/main/resources/DeviceDictionary_Auto.txt；</li>
 *     <li>更新版本号发布后供其他项目使用。</li>
 * </ul>
 *
 * @author allen
 * @date 2022-02-25
 * @since 1.0
 */
public class DeviceUpdateTest {

    private static String brands = "/Users/huxiao/Documents/github/MobileModels/brands";

    private static final List<String> list = Lists.newArrayList("行业", "7.", "10.", "8.", "9.", "8 英寸", "7 英寸", "10 倍",
            "(", "公开", "移动", "联通", "全网", "电信", "双", "至尊",
            "Wi-Fi", "3G", "4G", "5G", "LTE",
            "印度版", "北美版", "国际版", "欧洲版", "超级", "标准版", "梦镜版", "高配", "轻奢版", "增强版", "幻彩版", "青春", "优享版",
            "透明", "屏幕", "顶配版", "黑色", "全", "尊享版", "美图", "巴西版", "台湾", "/", "MTK", "高通", "保时捷", "真皮", "四核",
            "畅玩版", "真八核", "Prime", "Lite", "通用版", "太子妃版", "未知", "精英版", "YunOS", "标配版", "edge", "Star", "Alpha",
            "极速版","经典版","畅享版","电竞","文艺","特别"
    );

    public enum FileNameMapping {
        _360("360shouji.md", "360"),
        AppleAll("apple_all.md", "Apple"),
        AppleAllEn("apple_all_en.md", "Apple"),
        AppleCn("apple_cn.md", "Apple"),
        GoogleEn("google_en.md", "Google"),
        Honor("honor_cn.md", "Honor"),
        HonorEn("honor_global_en.md", "Honor"),
        Huawei("huawei_cn.md", "Huawei"),
        HuaweiEn("huawei_global_en.md", "Huawei"),
        Lenovo("lenovo.md", "Lenovo"),
        Letv("letv.md", "Letv"),
        Meizu("meizu.md", "Meizu"),
        MeizuEn("meizu_en.md", "Meizu"),
        XiaomiTv("mitv_cn.md", "Xiaomi"),
        XiaomiTvEn("mitv_global_en.md", "Xiaomi"),
        Motorola("motorola.md", "Motorola"),
        Nokia("nokia.md", "Nokia"),
        Nubia("nubia.md", "Nubia"),
        OnePlus("oneplus.md", "OnePlus"),
        OnePlusEn("oneplus_en.md", "OnePlus"),
        OPPO("oppo_cn.md", "OPPO"),
        OPPOEn("oppo_global_en.md", "OPPO"),
        Realme("realme_cn.md", "Realme"),
        RealmeEn("realme_global_en.md", "Realme"),
        Samsung("samsung_cn.md", "Samsung"),
        SamsungEn("samsung_global_en.md", "Samsung"),
        Smartisan("smartisan.md", "Smartisan"),
        Vivo("vivo.md", "Vivo"),
        Xiaomi("xiaomi.md", "Xiaomi"),
        XiaomiEn("xiaomi_en.md", "Xiaomi"),
        Zhixuan("zhixuan.md", "Huawei"),
        ZTE("zte.md", "ZTE"),
        ASUS("asus.md", "Asus"),
        BlackShark("blackshark.md", "BlackShark"),
        BlackSharkEn("blackshark_en.md", "BlackShark"),
        SonyCn("sony_cn.md", "Sony"),
        Coolpad("coolpad.md", "Coolpad"),
        VivoCn("vivo_cn.md", "Vivo"),
        VivoGlobalEn("vivo_global_en.md", "Vivo"),
        XiaomiWear("xiaomi-wear.md", "Xiaomi"),
        Google("google.md", "Google"),
        Nothing("nothing.md", "Nothing")
        ;

        private final String fileName;
        private final String brand;

        FileNameMapping(String fileName, String brand) {
            this.fileName = fileName;
            this.brand = brand;
        }

        public static FileNameMapping of(String fileName) {
            for (FileNameMapping value : values()) {
                if (StringUtils.equals(fileName, value.fileName)) {
                    return value;
                }
            }
            throw new NullPointerException("Not found mapping of: " + fileName);
        }

    }

    private static List<String> fix(List<String> lines) {
        List<String> res = Lists.newArrayList();
        for (String line : lines) {
            String fix = fix(line);
            if (StringUtils.isNotBlank(fix)) {
                res.add(fix);
            }
        }
        return res;
    }

    private static String fix(String line) {
        if (StringUtils.isBlank(line)) {
            return null;
        }
        if (StringUtils.startsWith(line, "-")) {
            return null;
        }
        return line.replaceAll("`|#|\\*", "").trim();
    }

    public static void main(String[] args) throws IOException {
        // 从这个 Git 克隆到本地：https://gitee.com/mirrors_KHwang9883/MobileModels/tree/master/brands

        Map<String, Pair<String, String>> map = Maps.newHashMap();

        Collection<File> files = FileUtils.listFiles(new File(brands), new String[]{"md"}, true);
        for (File file : files) {
            System.out.println("Starting process: " + file);
            String srcFileName = file.getName();

            FileNameMapping mapping = FileNameMapping.of(srcFileName);

            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            List<String> fixed = fix(lines);

            // 写入目标文件
            File dest = new File("src/test/resources/modules/" + StringUtils.removeEnd(srcFileName, ".md") + ".txt");
            FileUtils.writeLines(dest, fixed);
            System.out.println("Write to file: " + dest.getAbsolutePath());

            map.put(dest.getAbsolutePath(), Pair.of("Phone", mapping.brand));
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String output = "src/test/resources/" + date + "_DeviceDictionary_Auto.txt";
        execute(map, output);
    }

    private static void execute(Map<String, Pair<String, String>> map, String output) {
        try {
            List<String> list = Lists.newArrayList();
            for (Map.Entry<String, Pair<String, String>> entry : map.entrySet()) {
                List<String> lines = getLines(entry.getKey(), entry.getValue());
                if (lines != null && lines.size() > 0) {
                    list.addAll(lines);
                }
            }
            write2File(output, list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> getLines(String fileName, Pair<String, String> pair) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<String> result = Lists.newArrayList();

        Function<String, String> func = new Function<String, String>() {
            @Override
            public String apply(String input) {
                int index = 100000000;
                for (String key : list) {
                    if (!input.contains(key)) {
                        continue;
                    }

                    int temp = input.indexOf(key);
                    if (temp > -1 && temp < index) {
                        index = temp;
                    }

                }
                if (index == 100000000) {
                    return input;
                }
                return input.substring(0, index).trim();
            }
        };

        while ((line = reader.readLine()) != null) {
            if (line.trim().endsWith(":")) {
                continue;
            }

            String[] arr = StringUtils.splitPreserveAllTokens(line, ":");
            if (arr.length < 2) {
                continue;
            }
            result.add(String.format("%s,,%s,,%s,,%s,,5.5\n", arr[0].trim(), pair.getRight(), func.apply(arr[1]).trim(), pair.getLeft()));

        }

        return result;
    }

    private static void write2File(String output, List<String> lines) throws IOException {
        FileWriter writer = new FileWriter(output);
        for (String line : lines) {
            writer.write(line);
        }
        writer.flush();
        writer.close();
    }
}
