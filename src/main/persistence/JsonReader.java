package persistence;


import model.Course;
import model.Student;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads student from JSON data stored in file
// TODO citation : code taken and used from JsonSerializationDemo
//                 URL:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads student from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Student read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudent(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses student from JSON object and returns it
    private Student parseStudent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int studentNumber = jsonObject.getInt("studentNumber");
        Student student = new Student(name, studentNumber);
        addCourses(student, jsonObject);
        return student;
    }

    // MODIFIES: student
    // EFFECTS: parses courses from JSON object and adds them to student
    private void addCourses(Student student, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(student, nextCourse);
        }
    }

    // MODIFIES: student
    // EFFECTS: parses course from JSON object and adds it to student
    private void addCourse(Student student, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        String lectureDays = jsonObject.getString("lectureDays");
        String lectureTiming = jsonObject.getString("lectureTiming");
        Course course = new Course(name, startDate, endDate, lectureDays, lectureTiming);
        student.addCourse(course);
    }
}
