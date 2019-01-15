package com.loveayc.utildemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author 王文博
 * @Title: IDCardValidDate
 * @ProjectName utildemo
 * @Description: TODO
 * @date 2019/1/1516:54
 */
public class IDCardValidDate {
    //省(直辖市)码表
    private static String provinceCode[] = { "11", "12", "13", "14", "15", "21", "22",
            "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
            "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
            "64", "65", "71", "81", "82", "91" };
    //身份证前17位每位加权因子
    private static int[] powFactor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    //身份证第18位校检码
    private static char[] verifyValue = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    /**
     * 身份证正则表达式
     * 1.以非0位开头的数字6位 1 -6位。①1-2 升级行政区代码 ②3-4 地级行政区划分代码 ③5-6 县区行政区分代码
     * 2.以19或20开头的的8位yyyymmdd的日期数据。 7 - 14位。
     * 3.15-17 顺序码，同一地区同年、同月、同日出生人的编号，奇数是男性，偶数是女性
     * 4.18 校验码，如果是0-9则用0-9表示，如果是10则用X（罗马数字10）表示
     */
    private static String pattern = "^[1-9][0-9]{5}(?:19|20)?[0-9]{2}(?:0[1-9]|1[012])(?:0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9X]?$";
    private static Pattern regex = Pattern.compile(pattern);

    //校验的方法，判断是否为空，和各位数字是否准确
    public static boolean check(String idNum){
        // toUpperCase() 方法将字符串小写字符转换为大写
        idNum = idNum.toUpperCase();
        if(null == idNum || idNum.trim().length() != 18 ){
            return false;
        }
        char checkValue = getCheckValue(idNum);
        if(regex.matcher(idNum).find() && isValidProvinceId(idNum.substring(0,2)) && checkValue == idNum.charAt(17) && isValidDate(idNum.substring(6,14))){
            return true;
        }
        return false;
    }

    /**
     * 检查身份证的省份信息是否正确
     * @param provinceId
     * @return
     */
    private static boolean isValidProvinceId(String provinceId){
        for (String id : provinceCode) {
            if (id.equals(provinceId)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 校验一个日期的合法性，排除如2月30日这种日期
     * @param str
     * @return
     */
    private static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /**
     * 获取最后一位权重数
     * @param idNum
     * @return
     */
    private static char getCheckValue(String idNum) {
        int sum = 0;
        for (int i = 0; i < 17; i++)
            {
                sum = sum + Integer.parseInt(idNum.substring(i, i + 1)) * powFactor[i];
            }
        return verifyValue[sum % 11];
    }
}
