package persistence;


import model.Course;
import model.Student;
import model.StudentTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// TODO citation : code taken and used from JsonSerializationDemo
//                 URL:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Student student = new Student("My Student", 12222);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStudent() {
        try {
            Student student = new Student("My student", 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudent.json");
            writer.open();
            writer.write(student);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStudent.json");
            student = reader.read();
            assertEquals("My student", student.getName());
            assertEquals(0, student.getStudentNumber());
            assertEquals(0, student.getAllCourses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudent() {
        try {
            Student student = new Student("Soham Ray", 12345678);
            Course c1 = new Course("CPSC 110", "12/12/12", "21/21/21",
                    "Monday, Tuesday", "12:00 to 2:00");
            Course c2 = new Course("ASTR 101", "34/34/34", "23/23/23",
                    "Monday, Wednesday", "34:00 to 23:00");
            student.addCourse(c1);
            student.addCourse(c2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(student);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            student = reader.read();
            assertEquals("Soham Ray", student.getName());
            assertEquals(12345678, student.getStudentNumber());
            List<Course> courses = student.getAllCourses();
            assertEquals(2, courses.size());
            checkCourse("CPSC 110", "12/12/12", "21/21/21",
                    "Monday, Tuesday", "12:00 to 2:00", courses.get(0));
            checkCourse("ASTR 101", "34/34/34", "23/23/23",
                    "Monday, Wednesday", "34:00 to 23:00", courses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}