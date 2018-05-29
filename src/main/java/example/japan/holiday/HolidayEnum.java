package example.japan.holiday;

public enum HolidayEnum {
    
    
    COMPENSATORY_DAY(0, "振替休日"),
    
    NEW_YEARS_DAY(1, "元日"),
    
    COMING_OF_AGE_CEREMONY(2, "成人の日"),
    
    NATIONAL_FOUNDATION_DAY(3, "建国記念日"),
    
    SPRING_EQUINOX_DAY(4, "春分の日"),
    
    SHOWA_DAY(5, "昭和の日"),
    
    CONSTITUTION_DAY(6, "憲法記念日"),
    
    GREENERY_DAY(7, "みどりの日"),
    
    CHILDRENS_DAY(8, "こどもの日"),
    
    MARINE_DAY(9, "海の日"),
    
    MOUNTAIN_DAY(10, "山の日"),
    
    RESPECT_FOR_THE_AGED_DAY(11, "敬老の日"),
    
    HEALTH_SPORTS_DAY(12, "体育の日"),
    
    CULTURE_DAY(13, "文化の日"),
    
    LABOUR_THANKSGIVING_DAY(14, "勤労感謝の日"),
    
    AUTUMNAL_EQUINOX_DAY(15, "秋分の日"),
    
    EMPERORS_BIRTHDAY(16, "天皇誕生日");
    
    private Integer id;
    
    private String name;
    
    HolidayEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
