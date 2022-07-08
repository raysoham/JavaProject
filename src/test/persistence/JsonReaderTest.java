package persistence;


import model.Course;
import model.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// TODO citation : code taken and used from JsonSerializationDemo
//                 URL:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Student student = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudent.json");
        try {
            Student student = reader.read();
            assertEquals("My student", student.getName());
            assertEquals(0, student.getStudentNumber());
            assertEquals(0, student.getAllCourses().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudent.json");
        try {
            Student student = reader.read();
            assertEquals("Soham Ray", student.getName());
            assertEquals(12345678, student.getStudentNumber());
            List<Course> courses = student.getAllCourses();
            assertEquals(2, courses.size());
            checkCourse("CPSC 110", "12/12/12", "21/21/21",
                    "Monday, Tuesday", "12:00 to 2:00", courses.get(0));
            checkCourse("ASTR 101", "34/34/34", "23/23/23",
                    "Monday, Wednesday", "34:00 to 23:00", courses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}