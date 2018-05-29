package example.japan.era;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import example.utils.CalendarUtils;

/**
 * 明治以降の元号と略元号取得
 * 
 * @author T.Cho
 */
public class Era {

    private static DateTimeFormatter yearMonthDayFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * 現在の略元号
     * @return 略元号
     */
    public static String getCurrentEraElli() {
        return getEra(null, true);
    }

    /**
     * 現在の元号
     * @return 元号
     */
    public static String getCurrentEraName() {
        return getEra(null, false);
    }

    /**
     * 日付オブジェクトで略元号取得
     * @param date 日付オブジェクト
     * @return 略元号
     */
    public static String getEraElliByDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return getEra(dateTime, true);
    }
    
    /**
     * 日付オブジェクトで元号取得
     * @param date 日付オブジェクト
     * @return 元号
     */
    public static String getEraNameByDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return getEra(dateTime, false);
    }

    /**
     * 日付文字列(YYYY-MM-DD)で略元号を取得
     * @param str 日付文字列
     * @return 略元号
     */
    public static String getEraElliByDateString(String str) {
        DateTime dateTime = DateTime.parse(str, yearMonthDayFormat);
        return getEra(dateTime, true);
    }

    /**
     * 日付文字列(YYYY-MM-DD)で元号を取得
     * @param str 日付文字列
     * @return 元号
     */
    public static String getEraNameByDateString(String str) {
        DateTime dateTime = DateTime.parse(str, yearMonthDayFormat);
        return getEra(dateTime, false);
    }
    
    /**
     * 日付文字列で略元号を取得
     * @param str 日付文字列
     * @param formatter 日付文字列フォーマット
     * @return 略元号
     */
    public static String getEraElliByDateString(String date, DateTimeFormatter formatter) {
        DateTime dateTime = DateTime.parse(date, formatter);
        return getEra(dateTime, true);
    }

    /**
     * 日付文字列で元号を取得
     * @param str 日付文字列
     * @param formatter 日付文字列フォーマット
     * @return 元号
     */
    public static String getEraNameByDateString(String date, DateTimeFormatter formatter) {
        DateTime dateTime = DateTime.parse(date, formatter);
        return getEra(dateTime, false);
    }

    /**
     * 日時オブジェクトで略元号取得
     * @param dateTime 日時オブジェクト
     * @return 略元号
     */
    public static String getEraElliByDateTime(DateTime dateTime) {
        return getEra(dateTime, true);
    }

    /**
     * 日時オブジェクトで元号取得
     * @param dateTime 日時オブジェクト
     * @return 元号
     */
    public static String getEraNameByDateTime(DateTime dateTime) {
        return getEra(dateTime, false);
    }

    /**
     * 元号取得
     * @param dateTime 日時オブジェクト
     * @param elliFlag 略フラグ
     * @return 元号
     */
    private static String getEra(DateTime dateTime, boolean elliFlag) {
        JSONArray eraArr = initConfig();
        if (dateTime == null) {
            dateTime = new DateTime();
        }
        for (int index = 0; index < eraArr.size(); index++) {
            JSONObject eraJSONObj = eraArr.getJSONObject(index);
            if (StringUtils.isEmpty(eraJSONObj.getString("Start"))) {
                return "UNKNOWN";
            } else {
                DateTime start = DateTime.parse(eraJSONObj.getString("Start"), yearMonthDayFormat);
                if ((dateTime.isAfter(start) || dateTime.isEqual(start)) && StringUtils.isEmpty(eraJSONObj.getString("End"))) {
                    if (elliFlag) {
                        return eraJSONObj.getString("Ellipsis");
                    } else {
                        return eraJSONObj.getString("Name");
                    }
                } else if (dateTime.isAfter(start) || dateTime.isEqual(start)) {
                    DateTime end = DateTime.parse(eraJSONObj.getString("End"), yearMonthDayFormat);
                    if (dateTime.isBefore(end) || dateTime.isEqual(end)) {
                        if (elliFlag) {
                            return eraJSONObj.getString("Ellipsis");
                        } else {
                            return eraJSONObj.getString("Name");
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * JSONファイルから設定を求める
     * @return
     */
    private static JSONArray initConfig() {
        JSONObject obj = JSON.parseObject(CalendarUtils.getEra(""));
        JSONArray eraArr = obj.getJSONArray("Era");
        return eraArr;
    }
}
