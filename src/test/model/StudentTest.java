package model;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.CourseNotPresentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StudentTest {
    Student testStudent;
    Course testCourse1;
    Course testCourse2;

    @BeforeEach
    public void setup() {
        testStudent= new Student("Student Name", 12345); //12345 is Student Number
        testCourse1 = new Course("Test Course 1", "", "", "Monday, Wednesday, Friday",
                "");
        testCourse2 = new Course("Test Course 2", "", "", "Monday, Tuesday",
                "");
        testStudent.addCourse(testCourse1);
        testStudent.addCourse(testCourse2);
    }

    @Test
    public void testStudentConstructor() {
        assertEquals(testStudent.getName(), "Student Name");
        assertEquals(testStudent.getStudentNumber(), 12345);
    }

    @Test
    public void testAddCourse() {
        List<Course> courses = testStudent.getAllCourses();
        assertTrue(courses.contains(testCourse1));
        assertTrue(courses.contains(testCourse2));
    }

    @Test
    public void testDelCourseNoException() {
        try {
            testStudent.delCourse(testCourse1);
        } catch (CourseNotPresentException e) {
            fail("Exception should not have been thrown");
        }
        List<Course> courses = testStudent.getAllCourses();
        assertFalse(courses.contains(testCourse1));
        assertTrue(courses.contains(testCourse2));

    }

    @Test
    public void testDelCourseException() {
        try {
            testStudent.delCourse(testCourse1);
            List<Course> courses = testStudent.getAllCourses();
            assertFalse(courses.contains(testCourse1));
            testStudent.delCourse(testCourse1);
            fail("This message should not be reachable, exception should be thrown instead");
        } catch (CourseNotPresentException e) {
            // pass
        }
    }

    @Test
    public void testAllCoursesOnDayNoCourses() {
        String testString = testStudent.allCoursesOnDay("Thursday");
        assertEquals("No classes on Thursday!", testString);
    }

    @Test
    public void testAllCoursesOnDayMonday() {
        String testString = testStudent.allCoursesOnDay("Monday");
        assertEquals("Classes on Monday are Test Course 1, Test Course 2", testString);
    }

    @Test
    public void testAllCourseNames() {
        String testString1 = testStudent.allCourseNames();
        assertEquals("Test Course 1, Test Course 2", testString1);
        try {
            testStudent.delCourse(testCourse1);
        } catch (CourseNotPresentException e) {
            fail("Exception should not be thrown here");
        }
        String testString2 = testStudent.allCourseNames();
        assertEquals("Test Course 2", testString2);
    }
}
