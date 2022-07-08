package ui;

import exceptions.CourseNotPresentException;
import model.Course;
import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// UBC Manager Application
// TODO citation : code taken and used from JsonSerializationDemo
//                 URL:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class ManagerAppGUI extends JFrame implements ActionListener {

    private Student student;
    private List<Course> courseHolder = null;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private boolean deleteNow;
    private static final String JSON_STORE = "./data/student.json";

    BufferedImage image;

    JPanel startUpPage;
    JPanel studentInitPage;
    JPanel mainPage;

    JPanel containerPane;
    JPanel coursesContainer;

    JLabel studentInitName;
    JLabel studentInitStudentNumber;
    JTextField nameText;
    JTextField studentNumberText;
    JLabel mainName;
    JLabel mainNameIs;
    JLabel mainStudentNumber;
    JLabel mainStudentNumberIs;
    JLabel mainCourses;

    JButton loadExisting;
    JButton newStudent;
    JButton editDetails;
    JButton enter;
    JButton addCourse;
    JButton delCourse;
    JButton filter;
    JButton save;

    CardLayout cardLayout = new CardLayout();

    public ManagerAppGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("UBC MANAGER");
        containerPane = new JPanel(cardLayout);

        setupStartUpPage();
        setupStudentInitPage();
        setupMainPage();


        containerPane.add(startUpPage, "START");
        containerPane.add(studentInitPage, "STUDENT");
        containerPane.add(mainPage, "MAIN");
        cardLayout.show(containerPane, "START");

        add(containerPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setupStartUpPage() {
        startUpPage = new JPanel();
        startUpPage.setLayout(new BoxLayout(startUpPage, BoxLayout.PAGE_AXIS));

        startUpPageButtonsInit();

        startUpPage.add(Box.createRigidArea(new Dimension(0, 100)));
        startUpPage.add(loadExisting);
        startUpPage.add(Box.createRigidArea(new Dimension(0, 80)));
        startUpPage.add(newStudent);
        startUpPage.add(Box.createRigidArea(new Dimension(0, 100)));
        startUpPage.setBackground(new Color(0xA0F1F1));
    }

    private void startUpPageButtonsInit() {
        loadExisting = new JButton("Load existing student configuration");
        loadExisting.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadExisting.addActionListener(e -> loadExistingStudent());
        newStudent = new JButton("Create new student configuration");
        newStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        newStudent.addActionListener(e -> cardLayout.show(containerPane, "STUDENT"));
    }

    private void setupStudentInitPage() {
        studentInitPage = new JPanel();
        studentInitPage.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        studentInitPageComponentsInit();

        addToPanelWithGBC(studentInitPage, gbc, studentInitName, 0, 0, 1, 1);
        addToPanelWithGBC(studentInitPage, gbc, nameText, 2, 0, 3, 1);
        addToPanelWithGBC(studentInitPage, gbc, studentInitStudentNumber, 0, 1, 1, 1);
        addToPanelWithGBC(studentInitPage, gbc, studentNumberText, 2, 1, 3, 1);
        addToPanelWithGBC(studentInitPage, gbc, enter, 0, 2, 2, 1);


        studentInitPage.setBackground(new Color(0xA878DB));
    }

    private void studentInitPageComponentsInit() {
        studentInitName = new JLabel("Name:");
        studentInitStudentNumber = new JLabel("Student#:");
        nameText = new JTextField(20);
        studentNumberText = new JTextField(20);
        enter = new JButton("Enter");
        enter.addActionListener(e -> enterButton());
    }

    private void enterButton() {
        String name = nameText.getText();
        String studentNumberString = studentNumberText.getText();
        int studentNumber;
        try {
            studentNumber = Integer.parseInt(studentNumberString);
            mainNameIs.setText(name);
            mainStudentNumberIs.setText(studentNumberString);
            cardLayout.show(containerPane, "MAIN");
            student = new Student(name, studentNumber);
            if (courseHolder != null) {
                for (Course c : courseHolder) {
                    student.addCourse(c);
                }
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(studentInitPage,
                    "Please enter an integer value for student number", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupMainPage() {
        mainPage = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainPageComponentsInit();

        addToPanelWithGBC(mainPage, gbc, mainName, 0, 0, 1, 1);
        addToPanelWithGBC(mainPage, gbc, editDetails, 2, 0, 1, 1);
        addToPanelWithGBC(mainPage, gbc, mainNameIs, 1, 0, 1, 1);
        addToPanelWithGBC(mainPage, gbc, mainStudentNumber, 0, 1, 1, 1);
        addToPanelWithGBC(mainPage, gbc, mainStudentNumberIs, 1, 1, 1, 1);
        addToPanelWithGBC(mainPage, gbc, mainCourses, 0, 2, 1, 1);
        addToPanelWithGBC(mainPage, gbc, coursesContainer, 1, 2, 2, 2);
        addToPanelWithGBC(mainPage, gbc, addCourse, 0, 4, 1, 1);
        addToPanelWithGBC(mainPage, gbc, delCourse, 1, 4, 1, 1);
        addToPanelWithGBC(mainPage, gbc, filter, 2, 4, 1, 1);
        addToPanelWithGBC(mainPage, gbc, save, 1, 5, 1, 1);

        mainPage.setBackground(new Color(0x90EAA4));
    }

    private void mainPageComponentsInit() {
        mainName = new JLabel("Name:");
        editDetails = new JButton("Edit details");
        mainNameIs = new JLabel("Name is placeholder");
        mainStudentNumber = new JLabel("Student#:");
        mainStudentNumberIs = new JLabel("Student# is placeholder");
        mainCourses = new JLabel("Courses:");
        coursesContainer = new JPanel(new GridLayout(0, 2));
        addCourse = new JButton("Add Course");
        delCourse = new JButton("Delete Course");
        filter = new JButton("Filter courses by specific day of the week");
        save = new JButton("Save current configuration");
        mainPageButtonFunctionality();
    }

    private void mainPageButtonFunctionality() {
        editDetails.addActionListener(e -> cardLayout.show(containerPane, "STUDENT"));

        addCourse.addActionListener(e -> addCourseButton());

        delCourse.addActionListener(e -> delCourseButton());

        save.addActionListener(e -> saveButton());

        filter.addActionListener(e -> filterButton());
    }

    private void filterButton() {
        String day = JOptionPane.showInputDialog(mainPage, "Enter day for which you want to see courses");
        String requiredString = student.allCoursesOnDay(day);
        JOptionPane.showMessageDialog(mainPage, requiredString, "Filter", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveButton() {
        try {
            jsonWriter.open();
            jsonWriter.write(student);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        showImagePanel();
    }

    private void showImagePanel() {
        JDialog panel = new JDialog(this);
        JPanel imagePanel = new JPanel();
        try {
            image = ImageIO.read(new File("./data/mmmSave.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            imagePanel.add(picLabel);
            panel.add(imagePanel);
            panel.setTitle("Saving completed");
            panel.pack();
            panel.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            panel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExistingStudent() {
        try {
            student = jsonReader.read();
            courseHolder = student.getAllCourses();
            mainNameIs.setText(student.getName());
            nameText.setText(student.getName());
            mainStudentNumberIs.setText(String.valueOf(student.getStudentNumber()));
            studentNumberText.setText(String.valueOf(student.getStudentNumber()));
            for (Course c : student.getAllCourses()) {
                JButton newCourse = new JButton(c.getName());
                newCourse.setActionCommand(c.getName());
                newCourse.addActionListener(this);
                coursesContainer.add(newCourse);

                coursesContainer.revalidate();
                coursesContainer.repaint();
                coursesContainer.setBackground(mainPage.getBackground());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.out.println("Please make a new Student");
        }
        cardLayout.show(containerPane, "MAIN");
    }


    private void addCourseButton() {
        JButton newCourse;
        String name = JOptionPane.showInputDialog(mainPage, "Enter name of course");
        String startDate = JOptionPane.showInputDialog(mainPage, "Enter start date");
        String endDate = JOptionPane.showInputDialog(mainPage, "Enter end date");
        String lecDays = JOptionPane.showInputDialog(mainPage, "Enter lecture days");
        String lecTimings = JOptionPane.showInputDialog(mainPage, "Enter lecture timings");
        Course course = new Course(name, startDate, endDate, lecDays, lecTimings);
        student.addCourse(course);

        newCourse = new JButton(name);
        newCourse.setActionCommand(name);
        newCourse.addActionListener(this);
        coursesContainer.add(newCourse);

        coursesContainer.revalidate();
        coursesContainer.repaint();
        coursesContainer.setBackground(mainPage.getBackground());
    }

    private void delCourseButton() {
        if (deleteNow) {
            JOptionPane.showMessageDialog(mainPage,
                    "Please add a course before using the delete button!",
                    "No courses to delete", JOptionPane.WARNING_MESSAGE);
            deleteNow = false;
            delCourse.setBackground(new JButton().getBackground());
        } else {
            deleteNow = true;
            delCourse.setBackground(new Color(0xD04646));
        }

    }


    private void addToPanelWithGBC(JPanel panel, GridBagConstraints gbc, JComponent component, int x, int y,
                                   int width, int height) {
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String event = e.getActionCommand();
        if (!deleteNow) {
            for (Course c : student.getAllCourses()) {
                if (c.getName().equals(event)) {
                    showCourseDetails(c);
                }
            }
        } else {
            coursesContainer.remove(button);
            coursesContainer.revalidate();
            coursesContainer.repaint();
            coursesContainer.setBackground(mainPage.getBackground());
            actionPerformedHelper(event);
            deleteNow = false;
            delCourse.setBackground(new JButton().getBackground());
        }
    }

    private void actionPerformedHelper(String event) {
        for (Course c : student.getAllCourses()) {
            if (c.getName().equals(event)) {
                try {
                    student.delCourse(c);
                } catch (CourseNotPresentException cnpe) {
                    JOptionPane.showMessageDialog(mainPage,"Course has not been added to Student!");
                }
                break; // to guard against ConcurrentModificationException
            }
        }
    }


    private void showCourseDetails(Course c) {
        JDialog dialog = new JDialog(this);
        JPanel display = new JPanel(new GridLayout(0, 1));

        JLabel courseName = new JLabel("Course Name: " + c.getName());
        JLabel startAndEndDate = new JLabel("This course runs from " + c.getStartDate() + " to " + c.getEndDate());
        JLabel lecDays = new JLabel("Lectures are weekly on " + c.getLectureDays());
        JLabel lecTimings = new JLabel("Course lecture start and end time " + c.getLectureTiming());

        display.add(courseName);
        display.add(startAndEndDate);
        display.add(lecDays);
        display.add(lecTimings);

        dialog.setTitle(c.getName());
        dialog.add(display);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBackground(new Color(0xD4CD87));
        dialog.setVisible(true);
    }
}
