package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
    private Course testCourse;


    @Test
    public void testCourseConstructor() {
        testCourse = new Course("Course Name", "Start Date", "End Date", "Lecture " +
                "Days", "Lecture Timing");
        assertEquals(testCourse.getName(), "Course Name");
        assertEquals(testCourse.getStartDate(), "Start Date");
        assertEquals(testCourse.getEndDate(), "End Date");
        assertEquals(testCourse.getLectureDays(), "Lecture Days");
        assertEquals(testCourse.getLectureTiming(), "Lecture Timing");
    }



}
