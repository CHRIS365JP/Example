package example.japan.holiday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class Holiday {

    private static DateTimeFormatter yearMonthDayFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static DateTimeFormatter monthDayFormat = DateTimeFormat.forPattern("MM-dd");

    private DateTime newDateTime;
    
    private String oldHolidayFileName = "OldHoliday.json";
    
    private boolean oldHolidayFlag;

    public String getHolidayJSONString(Integer year) {
        return getHolidayJSONString(year, false);
    }
    
    public String getHolidayJSONString(Integer year, boolean formatFlag) {
        return getHolidayJSONString(year, formatFlag, false, null);
    }
    
    public String getHolidayJSONString(Integer year, boolean formatFlag, boolean oldHolidayFlag, String fileName) {
        
        this.oldHolidayFlag = oldHolidayFlag;
        if(oldHolidayFlag && StringUtils.isNotEmpty(fileName)) {
            oldHolidayFileName = fileName;
        }
        JSONArray resultArr = new JSONArray();
        JSONArray jsonArr = getHolidayList(year);
        Map<String, String> holidayMap = new HashMap<>();
        for (int index = 0; index < jsonArr.size(); index++) {
            JSONObject resultObj = new JSONObject();
            JSONObject holidayObj = jsonArr.getJSONObject(index);
            resultObj.put("Name", holidayObj.getString("Name"));
            // TYPE = 2 曜日固定の祝日
            if ("2".equals(holidayObj.getString("Type"))) {
                JSONArray settingArr = holidayObj.getJSONArray("Date");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, settingArr.getIntValue(0) - 1);
                calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, settingArr.getIntValue(1));
                calendar.set(Calendar.DAY_OF_WEEK, settingArr.getIntValue(2) + 1);
                calendar.set(Calendar.YEAR, year);
                DateTime dateTime = new DateTime(calendar.getTime());
                resultObj.put("Date", dateTime.toString(monthDayFormat));
            } 
            // TYPE = 3 春分の日と秋分の日
            else if ("3".equals(holidayObj.getString("Type"))) {
                if ("春分の日".equals(holidayObj.getString("Name"))) {
                    resultObj.put("Date", "03-" + calcSpringEquinoxDay(year));
                }
                if ("秋分の日".equals(holidayObj.getString("Name"))) {
                    resultObj.put("Date", "09-" + calcAutumnalEquinoxDay(year));
                }
            } 
            // TYPE = 1 日付固定の祝日 
            else {
                resultObj.put("Date", holidayObj.getString("Date"));
            }
            resultArr.add(resultObj);
        }

        // 日曜日と月曜日は連続祝日の場合
        for (int index = 0; index < resultArr.size(); index++) {
            JSONObject obj = resultArr.getJSONObject(index);
            if ("振替休日".equals(obj.getString("Name"))) {
                if (checkDuplication(holidayMap, obj.getString("Date"), year)) {
                    obj.put("Date", newDateTime.toString(monthDayFormat));
                }
            }
            holidayMap.put(obj.getString("Date"), obj.getString("Name"));
        }
        
        if(formatFlag) {
            return JSON.toJSONString(resultArr, true);
        } else {
            return resultArr.toJSONString();
        }
        
    }

    /**
     * 祝日と振替休日が重なるチェック
     * @param holidayMap マップ(key:日付,value:祝日名)
     * @param date 日付文字列
     * @param year 年
     * @return チェック結果
     */
    private boolean checkDuplication(Map<String, String> holidayMap, String date, Integer year) {
        DateTime dateTime = new DateTime(year + "-" + date.toString());
        int i = 0;
        while (holidayMap.containsKey(dateTime.plusDays(i).toString(monthDayFormat))) {
            i++;
        }
        newDateTime = dateTime.withDate(year, dateTime.getMonthOfYear(), dateTime.getDayOfMonth() + i);

        return true;
    }

    private JSONArray getHolidayList(Integer year) {

        // 返すリストを初期化
        JSONArray jsonArr = null;
        JSONArray resultArr = new JSONArray();
        // 設定ファイルを読み込み
        String jsonStr = getJSONData("Holiday.json");
        // JSONオブジェクトを作成
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        // 国民の祝日の開始日
        DateTime firstDateTiem = new DateTime(jsonObj.getDate("FirstDate"));
        // 国民の祝日の開始年
        Integer startYear = firstDateTiem.getYear();
        // 振替休日の開始日
        DateTime compensatoryDayStartDateTime = new DateTime(jsonObj.getDate("CompensatoryDayStartDate"));
        
        // 振替休日設置前
        if (year < compensatoryDayStartDateTime.getYear()) {
            resultArr = getHolidayJSONList(year, startYear, jsonArr, jsonObj);
        }
        // 振替休日
        else {
            resultArr = getHolidayJSONList(year, startYear, jsonArr, jsonObj);
            for (int i = 0; i < resultArr.size(); i++) {
                JSONObject holidayObj = resultArr.getJSONObject(i);
                
                // TYPE = 1 日付固定の祝日
                if ("1".equals(holidayObj.getString("Type"))) {
                    DateTime dateTime = new DateTime(year + "-" + holidayObj.getString("Date"));
                    // 日曜日と祝日が重なる場合
                    if (7 == dateTime.getDayOfWeek()) {
                        JSONObject compensatoryDay = new JSONObject();
                        compensatoryDay.put("Type", 1);
                        compensatoryDay.put("Date", dateTime.plusDays(1).toString(monthDayFormat));
                        compensatoryDay.put("Name", "振替休日");
                        resultArr.add(compensatoryDay);
                    }
                } 
                // TYPE = 3 春分の日と秋分の日
                else if ("3".equals(holidayObj.getString("Type"))) {
                    // 春分の日と秋分の日を算出
                    setSepcialHoliday(year, holidayObj);
                    DateTime dateTime = new DateTime(holidayObj.getString("Date"));
                    if (7 == dateTime.getDayOfWeek()) {
                        JSONObject compensatoryDay = new JSONObject();
                        compensatoryDay.put("Type", 1);
                        compensatoryDay.put("Date", dateTime.plusDays(1).toString(monthDayFormat));
                        compensatoryDay.put("Name", "振替休日");
                        resultArr.add(compensatoryDay);
                    }
                }
            }
            // 国民の休日
            if (year > DateTime.parse(jsonObj.getString("NationalHolidayStartDate"), yearMonthDayFormat).getYear()) {
                // 五月四日みどりの日を新設される前
                if (year < 2007) {
                    DateTime greeneryDay = new DateTime(year + "-05-04");
                    if (2 == greeneryDay.getDayOfWeek() || 3 == greeneryDay.getDayOfWeek() || 4 == greeneryDay.getDayOfWeek()
                            || 5 == greeneryDay.getDayOfWeek() || 6 == greeneryDay.getDayOfWeek()) {
                        JSONObject nationalHoliday = new JSONObject();
                        nationalHoliday.put("Type", 1);
                        nationalHoliday.put("Date", DateTime.parse(greeneryDay.toString(), monthDayFormat));
                        nationalHoliday.put("Name", "国民の休日");
                        resultArr.add(nationalHoliday);
                    }
                }
                // 敬老の日は曜日固定に移行(改定後、国民の休日が発生する可能性を生じる)
                if (year > 2002) {
                    // 秋分の日
                    String date = calcAutumnalEquinoxDay(year);
                    DateTime autumnalEquinoxDay = new DateTime(year + "-09-" + date);
                    // 秋分の日は水曜日の場合
                    if (3 == autumnalEquinoxDay.getDayOfWeek()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
                        calendar.set(Calendar.DAY_OF_WEEK, 2);
                        calendar.set(Calendar.MONTH, 8);
                        calendar.set(Calendar.YEAR, year);
                        DateTime respectForTheAgedDay = new DateTime(calendar);
                        // 敬老の日は秋分の日の2日前
                        if ((respectForTheAgedDay.getDayOfMonth() + 2) == Integer.valueOf(date)) {
                            JSONObject nationalHoliday = new JSONObject();
                            nationalHoliday.put("Type", 1);
                            nationalHoliday.put("Date", respectForTheAgedDay.plusDays(1).toString(monthDayFormat));
                            nationalHoliday.put("Name", "国民の休日");
                            resultArr.add(nationalHoliday);
                        }
                    }
                }
            }
        }

        return resultArr;
    }

    /**
     * 春分の日と秋分の日のJSONオブジェクトを作成
     * @param year 年
     * @param holidayObj JSONオブジェクト
     */
    private void setSepcialHoliday(Integer year, JSONObject holidayObj) {
        if ("春分の日".equals(holidayObj.getString("Name"))) {
            holidayObj.put("Date", year + "-03-" + calcSpringEquinoxDay(year));
        }
        // 秋分の日
        if ("秋分の日".equals(holidayObj.getString("Name"))) {
            holidayObj.put("Date", year + "-09-" + calcAutumnalEquinoxDay(year));
        }
    }

    /**
     * JSONファイルから祝日設定を求める
     * @param year 年
     * @param startYear 祝日法実施開始年
     * @param jsonArr JSON配列オブジェクト
     * @param jsonObj JSONオブジェクト
     * @return 年間祝日JSON配列
     */
    private JSONArray getHolidayJSONList(Integer year, Integer startYear, JSONArray jsonArr, JSONObject jsonObj) {
        JSONArray resultArr = new JSONArray();
        // 1948年の前
        if (year < startYear) {
            if(oldHolidayFlag) {
                JSONObject oldHolidayObj = JSON.parseObject(getJSONData(oldHolidayFileName));
                JSONArray oldHoliday = oldHolidayObj.getJSONObject("Holiday").getJSONArray(String.valueOf(year));
                resultArr.addAll(oldHoliday);
            }
            return resultArr;
        }
        // 1948年
        else if (year.equals(startYear)) {
            jsonArr = jsonObj.getJSONArray("Holiday");
            for (int i = 0; i < jsonArr.size(); i++) {
                JSONObject holidayJSONObj = jsonArr.getJSONObject(i);
                if (startYear.equals(holidayJSONObj.getInteger("Start"))) {
                    if(StringUtils.isNotEmpty(holidayJSONObj.getString("Date"))) {
                        DateTime holiday = new DateTime(year + "-" + holidayJSONObj.getString("Date"));
                        DateTime starDateTime = new DateTime(jsonObj.getString("FirstDate"));
                        if(holiday.isAfter(starDateTime)) {
                            resultArr.add(holidayJSONObj);
                        }
                    }
                    if("秋分の日".equals(holidayJSONObj.getString("Name"))) {
                        resultArr.add(holidayJSONObj);
                    }
                }
            }
            if(oldHolidayFlag) {
                JSONObject oldHolidayObj = JSON.parseObject(getJSONData(oldHolidayFileName));
                JSONArray oldHoliday = oldHolidayObj.getJSONObject("Holiday").getJSONArray("1948");
                
                resultArr.addAll(oldHoliday);
            }
        }
        // 1948年の後
        else {
            jsonArr = jsonObj.getJSONArray("Holiday");
            for (int i = 0; i < jsonArr.size(); i++) {
                JSONObject holidayJSONObj = jsonArr.getJSONObject(i);
                // 開始年の前
                // 終了年の後
                if (year < holidayJSONObj.getInteger("Start")
                        || (StringUtils.isNotEmpty(holidayJSONObj.getString("End")) && year > holidayJSONObj.getInteger("End"))) {
                } else {
                    resultArr.add(holidayJSONObj);
                }
            }
        }

        return resultArr;
    }

    /**
     * JSONファイルを読み込み
     * 
     * @param fileName ファイル名
     * @return ファイル内容
     */
    private String getJSONData(String fileName) {

        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String str = "";
        JSONReader reader = null;
        try {
            reader = new JSONReader(new FileReader(new File(classpath + fileName)));
            str = reader.readString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        return str;
    }

    /**
     * 春分の日を算出<br>
     * 有効範囲: 1796-2351
     * 
     * @param year 年
     * @return 日数
     */
    private String calcSpringEquinoxDay(Integer year) {
        return String.valueOf(calcSpringEquinoxDay(year, null, null));
    }
    
    /**
     * 春分の日を算出<br>
     * 
     * @param year 年
     * @return 日数
     */
    private String calcSpringEquinoxDay(Integer year, Double coefficientA, Double coefficientB) {
        // INT((0.242385544201545*A1)-(INT(A1/4)-INT(A1/100)+INT(A1/400))+20.9150411785049)
        // 係数a =  0.242385544201545
        // 係数b = 20.9150411785049
        // 有効範囲: 1796-2351
        double a = 0.242385544201545;
        double b = 20.9150411785049;
        if(coefficientA != null) {
            a = coefficientA;
        }
        if(coefficientB != null) {
            b = coefficientB;
        }
        Integer result = (int) (a * year - (year / 4 - year / 100 + year / 400) + b);
        return String.valueOf(result);
    }
    

    /**
     * 秋分の日を算出<br>
     * 有効範囲: 1604-2230
     * 
     * @param year 年
     * @return 日数
     */
    private String calcAutumnalEquinoxDay(Integer year) {
        return String.valueOf(calcAutumnalEquinoxDay(year, null, null));
    }
    
    /**
     * 秋分の日を算出
     * 
     * @param year 年
     * @return 日数
     */
    private String calcAutumnalEquinoxDay(Integer year, Double coefficientA, Double coefficientB) {
        // INT((0.242035499172366*A1)-(INT(A1/4)-INT(A1/100)+INT(A1/400))+24.0227494548387)
        // 係数a =  0.242035499172366
        // 係数b = 24.0227494548387
        // 有効範囲: 1604-2230
        double a = 0.242035499172366;
        double b = 24.0227494548387;
        if(coefficientA != null) {
            a = coefficientA;
        }
        if(coefficientB != null) {
            b = coefficientB;
        }
        Integer result = (int) (a * year - (year / 4 - year / 100 + year / 400) + b);
        return String.valueOf(result);
    }
}
