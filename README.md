# UBC Course Manager

## CPSC 210 Project by a8u1u

- I want to create an application which can keep track of a UBC *Student*'s 
details, and the *Course*s they are registered in with their details.
Courses can be added to or deleted from a Student, a singular Course's
details can be viewed, a Student's details can be viewed, etc. 
User stories will explain more detailed functionality. I may add further 
 functionality as the Project phases progress.

- This will be useful for Students trying to keep track of 
their courses and all the details.

- This is interesting to me as a first year student because I
had a hard time keeping track of all my courses, especially with
different timezones. It would have really helped me if this project
already existed as an application, but it didn't. So I thought I
will make it and in the future no one will have to struggle with
keeping a track of all their courses.

## User stories

- As a user, I want to be able to view my details
- As a user, I want to be able to add a Course
- As a user, I want to be able to delete an already added Course
- As a user, I want to be able to see all my Courses (by name)
- As a user, I want to be able to see the details of a particular Course
- As a user, I want to be able to see all the Courses on a particular day of the week 
- As a user, I want to be able to save details about Student, all fields, which includes all added Courses and their
details.
- As a user, I want to be able to load my saved Student configuration from file.
- As a user, I want to be able to see an alert and save my current Student configuration whenever I am about to quit the
program.
- As a user, I want to be able to see a message about previously saved Student whenever the program is run, and have an
option to load it.

## Phase 4: Task 2
I have made the Student class robust, more specifically the Student.delCourse() method. It throws a 
CourseNotPresentException if a course is not present in a Student's course list to be deleted.

## Phase 3: Task 2
If I had more time, I would have liked to refactor my ManagerAppGUI class;

- I would have made all the panels, buttons and dialogs into different classes extending JPanel, JButton and JDialog
respectively, to increase cohesion.
- I would have liked to learn more about how Date and Time is represented in Java and would have liked to add a
timezone changing functionality in my program.
