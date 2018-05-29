package example.japan.holiday;

public enum HolidayDateType {
    
    /**
     * 固定日付
     */
    DATE_FIXATION(1),
    /**
     * 固定曜日
     */
    WEEKDAY_FIXATION(2),
    /**
     * 天分観測
     */
    ASTRONOMICAL_OBSERVATION(3),
    /**
     * その他
     */
    ETC(0);
    
    private Integer id;
    
    HolidayDateType(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
}
