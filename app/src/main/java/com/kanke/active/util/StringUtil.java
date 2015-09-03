/**
 * ****************************************************************************
 *
 * @(#)StringUtil.java 2012-12-27
 * <p/>
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * *****************************************************************************
 */
package com.kanke.active.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2012-12-27 下午02:46:14
 */
public final class StringUtil {
    private static final String NULL = "null";

    private static final String EMPTY = "";
    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    private static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    private static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    private static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    private static final char DBC_SPACE = ' '; // 半角空格

    private StringUtil() {

    }

    /**
     * 把Object转为字符串
     */
    public static String parseObj2Str(Object object) {
        StringBuilder sb = new StringBuilder();
        if (object == null)
            return NULL;
        if (object instanceof Throwable) {
            Throwable e = (Throwable) object;
            sb.append(e);
            sb.append(e.getCause());
            StackTraceElement[] trace = e.getStackTrace();
            if (trace != null)
                for (StackTraceElement t : trace)
                    sb.append("\n\tat ").append(t);
            return sb.toString();
        } else if (object instanceof Object[]) {
            Object[] objectArray = (Object[]) object;
            sb.append(objectArray.getClass().getSimpleName()).append("[");

            for (Object _object : objectArray)
                sb.append(parseObj2Str(_object)).append(",");
            sb.append("]");
            return sb.toString();
        } else
            return object.toString();
    }

    /**
     * 获取字符串的MD5串
     *
     * @param src
     * @param char_set
     *            编码
     * @return src字符串对应的MD5串
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String string2Md5(String src, String char_set) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(src.getBytes(char_set));
        return bytes2Hex(messageDigest.digest());
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static String bytes2Hex(byte[] bArray) {
        StringBuilder sb = new StringBuilder();
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase(Locale.ENGLISH));
        }
        return sb.toString();
    }

    /**
     * 验证字符串是否等于空或等于null 或等于“null”
     *
     * @return
     */
    public static boolean isNull(String... srcs) {
        if (srcs == null || srcs.length == 0) {
            return true;
        }
        for (String src : srcs) {
            boolean isEmpty = EMPTY.equals(src);
            boolean isNull = NULL.equals(src);
            boolean isNull_S = src == null;
            return isEmpty || isNull || isNull_S;
        }
        return false;
    }

    /**
     * 验证输入字符串是否包包含空格，tab，是否为null
     *
     * @param str
     * @return
     */
    public static boolean containSpace(String str) {
        boolean result = false;
        if (str == null || str.equals(EMPTY)) {
            result = true;
        } else {
            char[] strChars = str.toCharArray();
            for (char strChar : strChars) {
                if (Character.isWhitespace(strChar)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 转换Object为String类型
     * @return String
     */
    public static String parseStr(Object object) {
        if (object != null)
            return String.valueOf(object).trim();
        else
            return null;
    }

    /**
     * 转换CharSequence为String类型
     *
     * @param sequence
     * @return String
     */
    public static String parseStr(CharSequence sequence) {
        if (sequence != null) {
            return String.valueOf(sequence);
        } else {
            return null;
        }
    }

    /**
     * <PRE>
     * 全角字符->半角字符转换   
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     * </PRE>
     */
    public static String qj2bj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <PRE>
     * 半角字符->全角字符转换   
     * 只处理空格，!到&tilde;之间的字符，忽略其他
     * </PRE>
     */
    public static String bj2qj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 提取信息中的网络链接:(h|H)(r|R)(e|E)(f|F) *= *('|")?(\w|\\|\/|\.)+('|"| *|>)?
     * 提取信息中的邮件地址:\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
     * 提取信息中的图片链接:(s|S)(r|R)(c|C) *= *('|")?(\w|\\|\/|\.)+('|"| |>)?
     * 提取信息中的IP地址:(\d+)\.(\d+)\.(\d+)\.(\d+) 提取信息中的中国手机号码:(86)*0*13\d{9}
     * 提取信息中的中国固定电话号码:(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}
     * 提取信息中的中国电话号码（包括移动和固定电话）:(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}
     * 提取信息中的中国邮政编码:[1-9]{1}(\d+){5} 提取信息中的中国身份证号码:\d{18}|\d{15} 提取信息中的整数：\d+
     * 提取信息中的浮点数（即小数）：(-?\d*)\.?\d+ 提取信息中的任何数字 ：(-?\d*)(\.\d+)?
     * 提取信息中的中文字符串：[\u4e00-\u9fa5]* 提取信息中的双字节字符串 (汉字)：[^\x00-\xff]*
     */

    public static final String PATTERN_TEL = "(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)";

    public static boolean isEmail(String email) {
        String str = "([a-zA-Z0-9_\\-]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z0-9_\\-]+)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 电话号码验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if(str.length() >9)
        {   m = p1.matcher(str);
            b = m.matches();
        }else{
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 从输入字符串中提取对应pattern的内容
     *
     * @param res
     * @param pattern_str
     * @return
     */
    public static String getSpecContent(String res, String pattern_str) {
        Pattern pattern = Pattern.compile(pattern_str);
        Matcher matcher = pattern.matcher(res);
        StringBuilder bf = new StringBuilder(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append("|");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }

    /**
     * md5加密字符串
     *
     * @param plainText
     * @return
     */

    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /**
     * 组装创业榜url
     *
     * @param type
     * @param row
     * @param id
     * @return
     */
    public static String creatUrlForTerm(int type, int row, int id, int userId) {
        StringBuffer sb = new StringBuffer();
        if (type == 0) {
            sb.append("row=").append(row).append("&").append("id=").append(id);

        } else {
            sb.append("type=").append(type).append("&").append("row=").append(row).append("&").append("id=").append(id);
        }
        return sb.toString();
    }

    public static String creatUrlForTerme(int type, int row, int id, int userId) {
        StringBuffer sb = new StringBuffer();
        if (userId != 0) {
            sb.append("userId=").append(userId).append("&").append("type=").append(type).append("&").append("row=").append(row).append("&").append("id=").append(id);
        }
        return sb.toString();
    }

    public static String creatUrlFocus(int toid, int type) {
        StringBuffer sb = new StringBuffer();
        sb.append("toid=").append(toid).append("&").append("type=").append(type);
        return sb.toString();
    }

    public static String creatUrlForTreat(int userId, int row, int id) {

        StringBuffer sb = new StringBuffer();
        sb.append("userId=").append(userId).append("&").append("row=").append(row).append("&").append("id=").append(id);

        return sb.toString();

    }

    public static String creatUrlForTerm2(int row, int id, int Nearby) {
        StringBuffer sb = new StringBuffer();
        sb.append("Nearby=").append(Nearby).append("&").append("row=").append(row).append("&").append("id=").append(id);

        return sb.toString();
    }

    /**
     * 组装距离最近的参数
     *
     * @param row
     * @param id
     * @return
     */
    public static String creatUrlForTerm3(int Nearby, int row, int id, double d, double e) {
        StringBuffer sb = new StringBuffer();
        sb.append("Nearby=").append(Nearby).append("&").append("row=").append(row).append("&").append("id=").append(id)
                .append("&").append("lat=").append(d).append("&").append("lng=").append(e);
        return sb.toString();
    }

    /**
     * 组装发现合伙人和顾问url
     *
     * @param type
     * @param row
     * @param id
     * @return
     */
    public static String creatUrlForDiscovery(String type, int row, int id, double d, double e) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNull(String.valueOf(d)) && StringUtil.isNull(String.valueOf(e))) {

            sb.append("type=").append(type).append("&").append("row=").append(row).append("&").append("id=").append(id);
        } else {

            sb.append("type=").append(type).append("&").append("row=").append(row).append("&").append("id=").append(id)
                    .append("&").append("lat=").append(d).append("&").append("lng=").append(e);
        }

        return sb.toString();
    }

    /**
     * 组装发现-项目url
     *
     * @param type
     * @param row
     * @param id
     * @return api/Product/GetProductByUser?userId=64&row=10&id=0
     */
    public static String creatUrlForEvent(String type, String row, String id, double d, double e) {

        StringBuffer sb = new StringBuffer();
        sb.append("type=").append(type).append("&").append("row=").append(row).append("&").append("id=").append(id)
                .append("&").append("lat=").append(d).append("&").append("lng=").append(e);
        return sb.toString();
    }

    public static String replaceShadow(String str) {
        return str.replaceAll("\"", " ");
    }

    public static String replaceDouHao(String str) {
        return replaceShadow(str).replaceAll(",", "/");
    }

    // 替换图片key
    public static String replacekey(String url, String defultKey, String newKey) {
        return url.replaceAll(defultKey, newKey);
    }

    public static String getGUID() {
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String a = uuid.toString();
        return a.toUpperCase();
    }

    public static String getFormatDetailString(String str) {

        String temp = StringUtil.replaceShadow(str);
        return temp.substring(0, temp.lastIndexOf("/"));
    }

    /**
     * 如果取得的值为空，不显示null，显示“暂无”
     */
    public static String ifGetNull(String str) {
        if (StringUtil.isNull(str)) {
            str = "暂无";
        }
        return str;
    }
}
