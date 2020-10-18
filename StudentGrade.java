package com.furkanozbudak;

import java.util.Random;

public class StudentGrade {
//    Student ID
//    Department ID
//    Course Number
//    Course Date (month, year)
//    Credits
//    Student Grade
    private String studentID;
    private String departmentID;
    private String courseNumber;
    private String courseDate;
    private String credits;
    private String studentGrade;

    public StudentGrade() {
    }

    public StudentGrade(String studentID, String departmentID, String courseNumber, String courseDate, String credits, String studentGrade) {
        this.studentID = studentID;
        this.departmentID = departmentID;
        this.courseNumber = courseNumber;
        this.courseDate = courseDate;
        this.credits = credits;
        this.studentGrade = studentGrade;
    }

    public StudentGrade generateRandomStudentGrade() {
        String studentID = String.valueOf(new Random().nextInt(1001));
        String departmentID = String.valueOf(new Random().nextInt(11));
        String courseNumber = String.valueOf(new Random().nextInt(501));
        String courseDate = "Not important";
        String credits = String.valueOf(new Random().nextInt(4));
        //String studentGrade = String.valueOf(1 + (4 - 1) * new Random().nextDouble());
        double studentGrade = 1 + (4 - 1) * new Random().nextDouble();
        studentGrade = studentGrade * 100;
        studentGrade = Math.round(studentGrade);
        studentGrade = studentGrade / 100;
        return new StudentGrade(studentID, departmentID, courseNumber,
                courseDate, credits, String.valueOf(studentGrade));
    }



    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }
}
