package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a course with a name, start date, end date, active days of the week, timing of lectures.
// assumes that all active days have same timings for lectures for a course
public class Course implements Writable {
    private String name;
    private String startDate;
    private String endDate;
    private String lectureDays;
    private String lectureTiming;


    public Course(String name, String startDate, String endDate, String lectureDays, String lectureTiming) {
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        setLectureDays(lectureDays);
        setLectureTiming(lectureTiming);
    }


    // getters
    public String getName() {
        return this.name;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getLectureDays() {
        return this.lectureDays;
    }

    public String getLectureTiming() {
        return this.lectureTiming;
    }


    //setters
    private void setName(String name) {
        this.name = name;
    }

    private void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private void setLectureDays(String lectureDays) {
        this.lectureDays = lectureDays;
    }

    private void setLectureTiming(String lectureTiming) {
        this.lectureTiming = lectureTiming;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("lectureDays", lectureDays);
        json.put("lectureTiming", lectureTiming);
        return json;
    }
}
