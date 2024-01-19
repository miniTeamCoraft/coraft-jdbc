package com.coraft.project.dto;

public class LectureDTO {
    private int lecCode;
    private String lecName;
    private String date;
    private String time;
    private int lecPrice;

    public LectureDTO() {}


    public LectureDTO(int lecCode, String lecName, String date, String time, int lecPrice) {
        this.lecCode = lecCode;
        this.lecName = lecName;
        this.date = date;
        this.time = time;
        this.lecPrice = lecPrice;
    }


    public int getLecCode() {
        return lecCode;
    }

    public void setLecCode(int lecCode) {
        this.lecCode = lecCode;
    }

    public String getLecName() {
        return lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLecPrice() {
        return lecPrice;
    }

    public void setLecPrice(int lecPrice) {
        this.lecPrice = lecPrice;
    }

    @Override
    public String toString() {
        return "[ 수강 신청 정보 ]" +
                "\n강의 이름 : " + lecName  + " | 날짜 : " + date  + " | 시간 : " + time  + " | 가격 : " + lecPrice + "원";
    }
}
