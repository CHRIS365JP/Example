package example.japan.holiday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

import example.japan.era.Era;

public class Mains {

//    public static void Main(String[] args) {
//        // TODO Auto-generated constructor stub
//        PropertiesUtil p = new PropertiesUtil("Holiday");
//        
//        String[] ss = p.getMessages("Holiday.Date");
//        System.out.println("length:" + ss.length);
//        for(String str : ss) {
//            System.out.println(str);
//        }
//    }
    public static void main(String[] args) {
        
//        System.out.println(Era.getCurrentEraElli());
//        System.out.println(Era.getCurrentEraName());
//        System.out.println(Era.getEraNameByDateString("1989-01-08"));
//        DateTimeFormatter yearMonthDayFormat = DateTimeFormat.forPattern("yyyy/MM/dd");
//        System.out.println(Era.getEraNameByDateString("1989/01/08", yearMonthDayFormat));
//        System.out.println(Era.getEraNameByDateString("1989-01-07"));
//        System.out.println(Era.getEraNameByDateString("1989/01/07", yearMonthDayFormat));
//        System.out.println(Era.getEraNameByDateString("1926-12-25"));
//        System.out.println(Era.getEraNameByDateString("1926/12/24", yearMonthDayFormat));
        
        System.out.println(Era.getEraNameByDateString("2926-12-25"));
//        Holiday h = new Holiday();
//        System.out.println(h.getHolidayJSONString(1947, true, true, null));
//        System.out.println(JSONArray.parseArray(h.getHolidayJSONString(1947, true, true, null)).size());
//        DateTime dt = new DateTime(2000 + "-" + "01-01");
//        
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
//        cal.set(Calendar.DAY_OF_WEEK, 2);
//        cal.set(Calendar.MONTH, 8);
//        cal.set(Calendar.YEAR, 2000);
//        //        cal.getTime().format("yyyy-MM-dd");
//        DateTime dt2 = new DateTime(cal.getTime());
//        System.out.println(dt2);

//        calNG = Calendar.getInstance();
//        calNG.set(Calendar.WEEK_OF_MONTH,3);
//        calNG.set(Calendar.DAY_OF_WEEK,2);
//        calNG.getTime().format("yyyy/MM/dd")
//        System.out.println(dt);
//        System.out.println(dt.plusDays(1).toString());
//        
//        DateTime respectForTheAgedDay = new DateTime();
//        respectForTheAgedDay = respectForTheAgedDay.withYear(2000).withMonthOfYear(9).withDayOfWeek(1)
//        respectForTheAgedDay.monthOfYear().setCopy(9);
//        respectForTheAgedDay.dayOfWeek().setCopy(1);
//System.out.println(respectForTheAgedDay.toString());
//        Holiday h = new Holiday();
//        JSONArray arr = h.getHolidayList(2000);
//        for(int i = 0; i < arr.size(); i ++) {
//            System.out.println("MAIN:" + arr.get(i));
//        }
//        System.out.println(h.getHolidayList(2000));
//        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        System.out.println(classpath);
//        String jsonStr = getJSONData("Holiday.json");
//        System.out.println(jsonStr);
//        JSONObject obj = JSON.parseObject(jsonStr);
//        JSONArray eraArray = obj.getJSONArray("Era");
//        JSONArray holidayArray = obj.getJSONArray("Holiday");
//        holidayArray.getJSONObject(index);
//        
//        for(Object o : holidayArray.) {
//            JSONObject obj1 = JSON.parseObject(o.toString());
//            obj1.
//            System.out.println(obj1);
//        }
//        System.out.println(eraArray);
//        System.out.println(holidayArray);
//        obj = obj.parseObject(ss);
//        System.out.println(obj.getInnerMap());
//        PropertiesUtil p = new PropertiesUtil("Holiday");
//        
//        Map ss = p.getMessage("Holiday.Date");
//        System.out.println(ss);
//        System.out.println("length:" + ss.length);
//        for(String str : ss) {
//            System.out.println(str);
//        }
    }
    
    public static String getJSONData(String fileName) {
        
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        JSONObject jsono = null;
        String str = "";
        try {
            JSONReader reader = new JSONReader(new FileReader(new File(classpath+ fileName)));
            str = reader.readString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return str;
    }
}
