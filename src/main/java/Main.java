import model.Grade;
import model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devmind_students");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner sc = new Scanner(System.in);
        help();

        String option = sc.nextLine();

        while (!option.equals("exit")) {
            String params;
            String[] paramsArray;
            switch (option.toUpperCase()) {
                case "1":
                    System.out.println("Enter first name, last name and email separated by commas");
                    params = sc.nextLine();
                    paramsArray = params.split(",");
                    if (paramsArray.length != 3) {
                        break;
                    }
                    Student student = new Student(paramsArray[0], paramsArray[1], paramsArray[2]);
                    addStudent(student, entityManager);
                    break;
                case "2":
                    System.out.println("Enter grade, observations and email separated by commas");
                    params = sc.nextLine();
                    paramsArray = params.split(",");
                    if (paramsArray.length != 3) {
                        break;
                    }
                    Grade grade = new Grade(Double.valueOf(paramsArray[0]), paramsArray[1]);
                    addGradeToStudent(grade, paramsArray[2], entityManager);
                    break;
                case "3":
                    System.out.println("Enter student email");
                    params = sc.nextLine();
                    checkStudentGrades(params, entityManager);
                    break;
            }
            System.out.println("Waiting for next operation");
            option = sc.nextLine();
        }

//        Student student = new Student("cristi", "nica", "nicacristi@ass");
//        addStudent(student, entityManager);
//        Grade grade = new Grade(8, "Tema  buna");
//        addGradeToStudent(grade, "nicacristi@ass", entityManager);
//        Student st = entityManager.find(Student.class, "nicacristi@ass");
//        System.out.println(st);
//        checkStudentGrades("nicacristi@ass", entityManager);

    }

    public static void help() {
        System.out.println("Press 1 for adding a new student;\nPress 2 for add a grade to a student;\nPress 3 for checking grades for a student");
    }

    public static boolean addStudent(Student student, EntityManager entityManager) {
        if (entityManager.find(Student.class, student.getEmail()) != null) {
            System.out.println("Student already exists");
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        return true;
    }

    public static boolean addGradeToStudent(Grade grade, String studentEmail, EntityManager entityManager) {
        Student student = entityManager.find(Student.class, studentEmail);
        if (student == null) {
            System.out.println("Student does not exist");
            return false;
        }
        grade.setStudent(student);
        entityManager.getTransaction().begin();
        entityManager.persist(grade);
       if (student.getGrades() == null) {
           student.setGrades(new HashSet<Grade>());
           student.getGrades().add(grade);
       } else {
           student.getGrades().add(grade);
       }
        entityManager.merge(student);
        entityManager.getTransaction().commit();
        return true;
    }

    public static boolean checkStudentGrades(String studentEmail, EntityManager entityManager) {
        Student student = entityManager.find(Student.class, studentEmail);
        if (student == null) {
            System.out.println("Student does not exist");
            return false;
        }
        if (student.getGrades() != null && !student.getGrades().isEmpty()) {
            System.out.println("Swhowing grades for student: " + student.getFirstName() + " " + student.getLastName());
            for (Grade grade : student.getGrades()) {
                System.out.println(grade);
            }
        }
        return true;
    }
}
