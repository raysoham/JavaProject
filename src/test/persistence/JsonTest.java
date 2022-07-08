package persistence;


import model.Course;

import static org.junit.jupiter.api.Assertions.*;

// TODO citation : code taken and used from JsonSerializationDemo
//                 URL:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkCourse(String name, String startDate, String endDate,
                               String lectureDays, String lectureTiming, Course course) {
        assertEquals(name, course.getName());
        assertEquals(startDate, course.getStartDate());
        assertEquals(endDate, course.getEndDate());
        assertEquals(lectureDays, course.getLectureDays());
        assertEquals(lectureTiming, course.getLectureTiming());
    }
}
