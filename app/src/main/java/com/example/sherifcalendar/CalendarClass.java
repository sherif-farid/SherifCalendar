package com.example.sherifcalendar;


public class CalendarClass {
    private  String dayTxt ;
    private int monthNumber ;
    private int year ;
    private int dayNumber ;
    private boolean isChecked ;
    private boolean isDay ;
    private boolean isInRange = false;
    private boolean isAvailable = true;
    public CalendarClass (String dayTxt ,int dayNumber, int monthNumber , int year
            , boolean isChecked , boolean isDay ){
        this.dayTxt = dayTxt ;
        this.monthNumber = monthNumber ;
        this.year = year;
        this.isChecked = isChecked ;
        this.isDay = isDay ;
        this.dayNumber = dayNumber ;
    }

    public String getDayTxt() {
        return dayTxt;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getYear() {
        return year;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle(){
        switch (monthNumber){
            case 0 :
                return "يناير " + year ;
            case 1 :
                return "فبراير " + year ;
            case 2 :
                return "مارس " + year ;
            case 3 :
                return "إبريل " + year ;
            case 4 :
                return "مايو " + year ;
            case 5 :
                return "يونيو " + year ;
            case 6 :
                return "يوليو " + year ;
            case 7 :
                return "أغسطس " + year ;
            case 8 :
                return "سبتمبر " + year ;
            case 9 :
                return "أكتوبر " + year ;
            case 10 :
                return "نوفمبر " + year ;
            case 11 :
                return "ديسمبر " + year ;
            default:
                return "يناير "  + (year+1) ;
        }
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }
    public String getFullDate(){
        String day =getDayTxt();
        String month = String.valueOf(getMonthNumber());
        String mYear = String.valueOf(getYear());
        if (day.length()==1){day = "0"+day;}
        if (month.length() == 1){month = "0"+month;}

        return  mYear+"-"+month+"-"+day;
    }

    public boolean isInRange() {
        if (isDay) {
            return isInRange;
        }else {
            return  false ;
        }
    }

    public void setInRange(boolean inRange) {
        isInRange = inRange;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}