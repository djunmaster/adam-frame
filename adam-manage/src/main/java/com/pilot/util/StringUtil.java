//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)

package com.pilot.util;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

public class StringUtil {
    private static final Logger logger = Logger.getLogger(StringUtil.class.getName());

    public StringUtil() {
    }

    public static boolean validEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotBlank(String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean validEmptyOrAll(String string) {
        return string != null && !string.isEmpty() && !"-1".equals(string);
    }

    public static String nullToEmpty(String string) {
        return string == null ? "" : string;
    }

    public static String VersionCompare(String version1, String version2) {
        String highVersion = "";
        if (validEmpty(version1) && validEmpty(version2)) {
            String[] verArr1 = version1.split("\\.");
            String[] verArr2 = version2.split("\\.");
            int length = Math.max(verArr1.length, verArr2.length);

            for (int i = 0; i < length; ++i) {
                int val1 = verArr1.length > i ? Integer.parseInt(verArr1[i]) : 0;
                int val2 = verArr2.length > i ? Integer.parseInt(verArr2[i]) : 0;
                if (val1 != val2) {
                    highVersion = val1 > val2 ? version1 : version2;
                    break;
                }
            }

            if (highVersion.isEmpty()) {
                highVersion = version1;
            }
        }

        return highVersion;
    }

    public static String listToString(List<Integer> list, char separator) {
        StringBuilder ret = new StringBuilder();
        if (list != null && !list.isEmpty()) {

            for (Integer item : list) {
                ret.append(item.toString()).append(separator);
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(ret.length() - 1);
            }
        }

        return ret.toString();
    }

    public static String listToString(List<Integer> list) {
        return listToString(list, ',');
    }

    public static String strListToString(List<String> list) {
        StringBuilder ret = new StringBuilder();
        if (list != null && !list.isEmpty()) {

            for (String item : list) {
                ret.append("'").append(item).append("',");
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(ret.length() - 1);
            }
        }

        return ret.toString();
    }

    public static String[] stringSplitToArray(String str, String expr) {
        return str.split(expr);
    }

    public static String strArrayToString(String[] arr, String separator) {
        StringBuilder ret = new StringBuilder();
        if (arr != null && arr.length > 0) {
            for (String s : arr) {
                ret.append(s).append(separator);
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(ret.length() - 1);
            }
        }

        return ret.toString();
    }

    public static String arrayToString(Integer[] arr) {
        return arrayToString(arr, ",");
    }

    public static String arrayToString(Integer[] arr, String separator) {
        StringBuilder ret = new StringBuilder();
        if (arr != null && arr.length > 0) {
            int var4 = arr.length;

            for (Integer i : arr) {
                ret.append(i).append(separator);
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(ret.length() - 1);
            }
        }

        return ret.toString();
    }

    public static Double doubleDivision(Double d1, BigInteger b2) {
        double ret = 0.0;
        if (!b2.equals(new BigInteger("0"))) {
            ret = d1 * 100.0 / (double) b2.intValue();
        }

        return (double) Math.round(ret * 10.0) / 10.0;
    }

    public static int StringParseInt(String str) {
        int result = 0;
        if (validEmpty(str)) {
            try {
                double d = Double.parseDouble(str.trim());
                result = (int) d;
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }
        }

        return result;
    }

    public static long StringParseLong(String str) {
        long result = 0L;
        if (validEmpty(str)) {
            try {
                Double d = Double.parseDouble(str.trim());
                result = d.longValue();
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }
        }

        return result;
    }

    public static boolean checkIdInList(String listStr, long id) {
        if (validEmpty(listStr) && id > 0L) {
            String[] idArr = listStr.split(",");
            int var5 = idArr.length;

            for (String s : idArr) {
                if (id == (long) StringParseInt(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String arrayToString(List<Long> ids) {
        return arrayToString(ids, ",");
    }

    public static String arrayToString(List<Long> ids, String separator) {
        StringBuilder ret = new StringBuilder();
        if (ids != null && !ids.isEmpty()) {

            for (Long i : ids) {
                ret.append(i).append(separator);
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(ret.length() - 1);
            }
        }

        return ret.toString();
    }


    public static boolean equalWithInteger(Integer first, Integer second) {
        if (first != null && second != null) {
            return first.equals(second);
        } else {
            return false;
        }
    }

    public static boolean equalWithLong(Long first, Long second) {
        if (first != null && second != null) {
            return first.intValue() == second.intValue();
        } else {
            return false;
        }
    }
}
