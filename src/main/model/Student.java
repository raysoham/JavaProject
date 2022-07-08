package model;

import exceptions.CourseNotPresentException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a student with name, student number and list of courses they are registered in
public class Student implements Writable {
    private String name;
    private int studentNumber; // Unique for every single student ever, should be used for lookup function(s)
    private List<Course> courses = new ArrayList<>();


    public Student(String name, int studentNumber) {
        setName(name);
        setStudentNumber(studentNumber);
    }

    // MODIFIES: this
    // EFFECTS: adds a course to courses
    public void addCourse(Course c) {
        this.courses.add(c);
    }


    // MODIFIES: this
    // EFFECTS: deletes c from courses
    public void delCourse(Course c) throws CourseNotPresentException {
        if (courses.contains(c)) {
            this.courses.remove(c);
        } else {
            throw new CourseNotPresentException();
        }
    }


    // EFFECTS: returns a string of all names of courses on a particular day
    public String allCoursesOnDay(String day) {
        String allCoursesOnDay = "";
        boolean noCourses = true;
        for (Course c : courses) {
            if (c.getLectureDays().contains(day)) {
                allCoursesOnDay += c.getName() + ", ";
                noCourses = false;
            }
        }
        if (noCourses) {
            return "No classes on " + day + "!";
        }
        String returnString = "Classes on " + day + " are " + allCoursesOnDay;
        return returnString.substring(0, returnString.length() - 2);
    }

    // EFFECTS: returns a string of all course names concatenated together separated by commas
    public String allCourseNames() {
        String allCourseNames = "";
        for (Course c : courses) {
            allCourseNames += c.getName() + ", ";
        }
        return allCourseNames.substring(0, allCourseNames.length() - 2);
    }


    //getters
    public String getName() {
        return this.name;
    }

    public int getStudentNumber() {
        return this.studentNumber;
    }

    public List<Course> getAllCourses() {
        return this.courses;
    }

    //setters
    private void setName(String name) {
        this.name = name;
    }

    private void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("studentNumber", studentNumber);
        json.put("courses", coursesToJson());
        return json;
    }

    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
