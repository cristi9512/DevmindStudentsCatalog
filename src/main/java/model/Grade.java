package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private double grade;

    private String observations;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Grade() {
    }

    public Grade(double grade, String observations) {
        this.grade = grade;
        this.observations = observations;
    }

    public Grade(double grade, String observations, Student student) {
        this.grade = grade;
        this.observations = observations;
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return id == grade1.id &&
                Double.compare(grade1.grade, grade) == 0 &&
                observations.equals(grade1.observations) &&
                student.equals(grade1.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, observations, student);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", grade=" + grade +
                ", observations='" + observations + '\'' +
                '}';
    }
}
