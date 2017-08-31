package com.kotlinformat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yin on 2017/5/23.
 */
public class ResUtil {

    public static String getUrlEncode(String s) throws Exception {
        String urlEncode = "";
        urlEncode = URLEncoder.encode(s, "UTF-8");
        return urlEncode;
    }

    /**
     * 格式化json
     *
     * @param content
     * @return
     */
    public static String formatJson(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(content);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    public static String decodeUnicode(String str) {
        Charset set = Charset.forName("UTF-16");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher(str);
        int start = 0;
        int start2 = 0;
        StringBuffer sb = new StringBuffer();
        while (m.find(start)) {
            start2 = m.start();
            if (start2 > start) {
                String seg = str.substring(start, start2);
                sb.append(seg);
            }
            String code = m.group(1);
            int i = Integer.valueOf(code, 16);
            byte[] bb = new byte[4];
            bb[0] = (byte) ((i >> 8) & 0xFF);
            bb[1] = (byte) (i & 0xFF);
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append(String.valueOf(set.decode(b)).trim());
            start = m.end();
        }
        start2 = str.length();
        if (start2 > start) {
            String seg = str.substring(start, start2);
            sb.append(seg);
        }
        return sb.toString();
    }

}
