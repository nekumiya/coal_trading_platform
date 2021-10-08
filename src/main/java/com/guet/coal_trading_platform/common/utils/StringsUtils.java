package com.guet.coal_trading_platform.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;


/**
 * String工具类
 * 提供：字符串判断、转换、下划线驼峰互转，UUID
 */
public class StringsUtils extends StringUtils {

    private StringsUtils(){

    }

    /**
     * 逗号
     */
    public static final String SEP_COMMA = ",";
    private static final String UNDER_LINE = "_";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_URL = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?";

    /**
     * 下划线转驼峰
     *
     * @param line  源字符串
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line) {
        StringBuilder result = new StringBuilder();
        if (line == null || line.isEmpty()) {
            return "";
        } else if (!line.contains(UNDER_LINE)) {
            // 不含下划线，仅将首字母小写
            return line.substring(0, 1).toLowerCase() + line.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = line.split(UNDER_LINE);
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        StringBuilder result = new StringBuilder();
        if (line != null && line.length() > 0) {
            // 将第一个字符处理成小写
            result.append(line.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < line.length(); i++) {
                String s = line.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (Character.isUpperCase(s.charAt(0)) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接拼接
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUuid(int number) {
        if (number < 1) {
            return new String[0];
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUuid();
        }
        return ss;
    }


    /**
     * 首字母变小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 首字母变大写, 利用ASCII编码进行转换
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        char[] charArray = str.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断是否空白
     *
     * @param str
     * @return
     */
    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否不是空白
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 检测输入的字符串数组是否都为空
     * @param strs
     * @return
     */
    public static boolean isNotBlank(final String...strs) {
        if (strs == null || strs.length < 1) {
            return false;
        }

        for (String str : strs) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串全部是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     *
     * @param strings
     * @return
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkValue为 null 或者为 "" 时返回 defaultValue
     *
     * @param checkValue
     * @param defaultValue
     * @return
     */
    public static String isEmpty(String checkValue, String defaultValue) {
        return isEmpty(checkValue) ? defaultValue : checkValue;
    }

    /**
     * 字符串不为 null 而且不为 "" 并且等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndEquelsOther(String str, String other) {
        if (isEmpty(str)) {
            return false;
        }
        return str.equals(other);
    }

    /**
     * 字符串不为 null 而且不为 "" 并且不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEmptyAndNotEquelsOther(String str, String... other) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < other.length; i++) {
            if (str.equals(other[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串不等于other
     *
     * @param str
     * @param other
     * @return
     */
    public static boolean isNotEquelsOther(String str, String... other) {
        for (int i = 0; i < other.length; i++) {
            if (other[i].equals(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为空
     *
     * @param strings
     * @return
     */
    public static boolean isNotEmpty(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较字符相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean equals(String value, String equals) {
        if (isAllEmpty(value, equals)) {
            return true;
        }
        return value.equals(equals);
    }

    /**
     * 比较字符串不相等
     *
     * @param value
     * @param equals
     * @return
     */
    public static boolean isNotEquals(String value, String equals) {
        return !equals(value, equals);
    }


    /**
     * 根据一个正则式，将字符串拆分成数组，空元素将被忽略
     *
     * @param s     字符串
     * @param regex 正则式
     * @return 字符串数组
     */
    public static String[] splitIgnoreBlank(String s, String regex) {
        return isEmpty(s) ?
                null : CommonUtils.removeIfEmpty(split(s, regex));
    }

    public static String[] splitIgnoreBlank(String s) {
        return splitIgnoreBlank(s, StringsUtils.SEP_COMMA);
    }

    /**
     * 将一个数组的部分元素转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param offset 开始元素的下标
     * @param len    元素数量
     * @param c      分隔符
     * @param objs   数组
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(int offset,
                                           int len,
                                           Object c,
                                           T[] objs) {
        StringBuilder sb = new StringBuilder();
        if (null == objs || len < 0 || 0 == objs.length) {
            return sb;
        }

        if (offset < objs.length) {
            sb.append(objs[offset]);
            for (int i = 1; i < len && i + offset < objs.length; i++) {
                sb.append(c).append(objs[i + offset]);
            }
        }
        return sb;
    }

    /**
     * 将一个集合转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c    分隔符
     * @param coll 集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Collection<T> coll) {
        StringBuilder sb = new StringBuilder();
        if (null == coll || coll.isEmpty()) {
            return sb;
        }
        return concat(c, coll.iterator());
    }

    /**
     * 将一个迭代器转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c  分隔符
     * @param it 集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Iterator<T> it) {
        StringBuilder sb = new StringBuilder();
        if (it == null || !it.hasNext()) {
            return sb;
        }
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(c).append(it.next());
        }
        return sb;
    }

    /**
     * 分割字符串为int集合
     *
     * @param str 要分割的字符串
     * @param sep 连接符
     * @return
     */
    public static List<Integer> splitToIntList(String str, String sep) {
        return splitToNumberList(str, sep, Integer.class);
    }

    /**
     * 分割字符串为数字集合
     *
     * @param str      要分割的字符串
     * @param sep      分割符
     * @param numClass 数字类型，目前支持:Integer,Long,Double,BigDecimal,Short,Byte
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T extends Number> List<T> splitToNumberList(String str, String sep, Class<T> numClass) {
        String[] strs = splitIgnoreBlank(str, sep);
        if (strs != null) {
            List<T> nums = CommonUtils.arrayList();
            for (String s : strs) {
                if (numClass == Integer.class) {
                    nums.add((T) Integer.valueOf(s));
                } else if (numClass == Long.class) {
                    nums.add((T) Long.valueOf(s));
                } else if (numClass == Double.class) {
                    nums.add((T) Double.valueOf(s));
                } else if (numClass == Short.class) {
                    nums.add((T) Short.valueOf(s));
                } else if (numClass == Byte.class) {
                    nums.add((T) Byte.valueOf(s));
                } else if (numClass == BigDecimal.class) {
                    nums.add((T) new BigDecimal(s));
                }
            }
            return nums;
        }
        return new ArrayList<T>();
    }

    public static boolean matched(String parameter, String pattern) {
        return isNoneBlank(parameter,parameter) && parameter.matches(pattern);
    }

    /**
     * 判断是否为邮箱
     *
     * @param parameter
     */
    public static boolean isEmail(String parameter) {
        return matched(parameter,REGEX_EMAIL);
    }

    /**
     * 判断是否URL
     *
     * @param parameter
     */
    public static boolean isUrl(String parameter) {
        return matched(parameter, REGEX_URL);
    }

    /**
     * 检查是否为手机号码
     */
    public final static String PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";
    public static boolean isPhone(String result) {
        return Pattern.compile(PHONE_PATTERN).matcher(result).matches();
    }
    
    /**
     * 原理:要得到的随机数的范围是[2,100]，假设返回的伪随机数的范围是[0,N)，也即[0,N-1];对得到的这个数模99，于是计算得到的数的范围是[0,98]；再把结果加2，范围就是[2,100]了。
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
    
    /***
     * Double转 String保留2位小数
     * @param value
     * @return
     */
    public static String doubleFormat2(Double value) {
    		return String.format("%.2f", value);
	}
    
    /**
	 * 计算当前月有多少天
	 * 
	 * @return
	 */
	public static int getDays(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
 
			}
		} else {
			// 闰年
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;
		}
		return days;
	}
}
