package com.furkanozbudak;

import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String args[]) {

        // generate student list
        List<StudentGrade> studentGradeList = new ArrayList<>();
        StudentGrade studentGrade = new StudentGrade();
        for (int i = 0; i < 100000; i++) {
            studentGradeList.add(studentGrade.generateRandomStudentGrade());
        }


        /** Sequential computation with loops starts **/
        long startTime = new Date().getTime();

        // find repetition of each student in the studentGrade list
        HashMap<String, Integer> studentRepetitionMap = new HashMap<>();
        for (StudentGrade sg : studentGradeList) {
            String studentID = sg.getStudentID();
            if (studentRepetitionMap.containsKey(studentID)) {
                studentRepetitionMap.put(studentID, studentRepetitionMap.get(studentID) + 1);
            } else {
                studentRepetitionMap.put(studentID, 1);
            }
        }

        // find GPA of each student
        double sum = 0;
        double gpa;
        HashMap<String, Double> studentGpaMap = new HashMap<>();
        for (String studentID : studentRepetitionMap.keySet()) {
            for (StudentGrade sg : studentGradeList) {
                if (sg.getStudentID().equals(studentID)) {
                    sum += Double.parseDouble(sg.getStudentGrade());
                }
            }
            gpa = sum / studentRepetitionMap.get(studentID);
            gpa = gpa * 100;
            gpa = Math.round(gpa);
            gpa = gpa / 100;
            studentGpaMap.put(studentID, gpa);
            sum = 0;
        }

        // find maximum GPA student
        double maxGpa = 0;
        String maxGpaStudentID = "";
        for (Map.Entry<String, Double> entry : studentGpaMap.entrySet()) {
            if (entry.getValue() > maxGpa) {
                maxGpa = entry.getValue();
                maxGpaStudentID = entry.getKey();
            }
        }

        // find minimum GPA student
        double minGpa = 4;
        String minGpaStudent = "";
        for (Map.Entry<String, Double> entry : studentGpaMap.entrySet()) {
            if (entry.getValue() < minGpa) {
                minGpa = entry.getValue();
                minGpaStudent = entry.getKey();
            }
        }

        // --------------------------------------------------------------------------- //

        // find Average GPA of each course

        HashMap<String, Integer> courseRepetitionMap = new HashMap<>();
        for (StudentGrade sg : studentGradeList) {
            String courseNumber = sg.getCourseNumber();
            if (courseRepetitionMap.containsKey(courseNumber)) {
                courseRepetitionMap.put(courseNumber, courseRepetitionMap.get(courseNumber) + 1);
            } else {
                courseRepetitionMap.put(courseNumber, 1);
            }
        }

        gpa = 0;
        HashMap<String, Double> courseGpaMap = new HashMap<>();
        for (String courseNumber : courseRepetitionMap.keySet()) {
            for (StudentGrade sg : studentGradeList) {
                if (sg.getCourseNumber().equals(courseNumber)) {
                    sum += Double.parseDouble(sg.getStudentGrade());
                }
            }
            gpa = sum / courseRepetitionMap.get(courseNumber);
            gpa = gpa * 100;
            gpa = Math.round(gpa);
            gpa = gpa / 100;
            courseGpaMap.put(courseNumber, gpa);
            sum = 0;
        }

        // --------------------------------------------------------------------------- //

        // find Average GPA of each department

        HashMap<String, Integer> departmentRepetitionMap = new HashMap<>();
        for (StudentGrade sg : studentGradeList) {
            String departmentID = sg.getDepartmentID();
            if (departmentRepetitionMap.containsKey(departmentID)) {
                departmentRepetitionMap.put(departmentID, departmentRepetitionMap.get(departmentID) + 1);
            } else {
                departmentRepetitionMap.put(departmentID, 1);
            }
        }

        gpa = 0;
        HashMap<String, Double> departmentGpaMap = new HashMap<>();
        for (String departmentID : departmentRepetitionMap.keySet()) {
            for (StudentGrade sg : studentGradeList) {
                if (sg.getDepartmentID().equals(departmentID)) {
                    sum += Double.parseDouble(sg.getStudentGrade());
                }
            }
            gpa = sum / departmentRepetitionMap.get(departmentID);
            gpa = gpa * 100;
            gpa = Math.round(gpa);
            gpa = gpa / 100;
            departmentGpaMap.put(departmentID, gpa);
            sum = 0;
        }

        long endTime = new Date().getTime();
        System.out.println("Sequential Loops Execution Time: " + (endTime - startTime) + " milliseconds");

        /** Sequential computation with loops ends **/

        /** Sequential computation with stream starts **/
        startTime = new Date().getTime();

        Map<String, List<StudentGrade>> studentRecordMap =
                studentGradeList.stream().collect(Collectors.groupingBy(sg -> sg.getStudentID()));

        Map<String, Double> sGPAm = new HashMap<>();

        studentRecordMap.keySet().stream().
                forEach(studentID -> {
                    List<StudentGrade> list = studentRecordMap.get(studentID);
                    double studentGpa = list.stream().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    studentGpa = studentGpa * 100;
                    studentGpa = Math.round(studentGpa);
                    studentGpa = studentGpa / 100;
                    sGPAm.put(studentID, studentGpa);
                });

        Optional<Map.Entry<String, Double>> maxEntry = sGPAm.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        String maxGpaStudentIDS = maxEntry.get().getKey();
        double maxGpaS = maxEntry.get().getValue();

        Map<String, List<StudentGrade>> departmentRecordMap =
                studentGradeList.stream().collect(Collectors.groupingBy(sg -> sg.getDepartmentID()));

        Map<String, Double> dGPAm = new HashMap<>();

        departmentRecordMap.keySet().stream().
                forEach(departmentID -> {
                    List<StudentGrade> list = departmentRecordMap.get(departmentID);
                    double departmentGpa = list.stream().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    departmentGpa = departmentGpa * 100;
                    departmentGpa = Math.round(departmentGpa);
                    departmentGpa = departmentGpa / 100;
                    dGPAm.put(departmentID, departmentGpa);
                });

        Map<String, List<StudentGrade>> courseRecordMap =
                studentGradeList.stream().collect(Collectors.groupingBy(sg -> sg.getCourseNumber()));

        Map<String, Double> cGPAm = new HashMap<>();

        courseRecordMap.keySet().stream().
                forEach(courseNumber -> {
                    List<StudentGrade> list = courseRecordMap.get(courseNumber);
                    double courseGPA = list.stream().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    courseGPA = courseGPA * 100;
                    courseGPA = Math.round(courseGPA);
                    courseGPA = courseGPA / 100;
                    cGPAm.put(courseNumber, courseGPA);
                });

        endTime = new Date().getTime();
        System.out.println("Sequential Streams Execution Time: " + (endTime - startTime) + " milliseconds");

        /** Sequential computation with stream ends **/

        /** Parallel computation with stream starts **/

        startTime = new Date().getTime();

        Map<String, List<StudentGrade>> studentRecordMapP =
                studentGradeList.stream().parallel().collect(Collectors.groupingBy(sg -> sg.getStudentID()));

        Map<String, Double> sGPAmP = new HashMap<>();

        studentRecordMap.keySet().stream().parallel().
                forEach(studentID -> {
                    List<StudentGrade> list = studentRecordMapP.get(studentID);
                    double studentGpa = list.stream().parallel().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    studentGpa = studentGpa * 100;
                    studentGpa = Math.round(studentGpa);
                    studentGpa = studentGpa / 100;
                    sGPAmP.put(studentID, studentGpa);
                });

        Optional<Map.Entry<String, Double>> maxEntryP = sGPAmP.entrySet()
                .stream().parallel()
                .max(Comparator.comparing(Map.Entry::getValue));
        String maxGpaStudentIDP = maxEntry.get().getKey();
        double maxGpaP = maxEntry.get().getValue();

        Map<String, List<StudentGrade>> departmentRecordMapP =
                studentGradeList.stream().parallel().collect(Collectors.groupingBy(sg -> sg.getDepartmentID()));

        Map<String, Double> dGPAmP = new HashMap<>();

        departmentRecordMap.keySet().stream().parallel().
                forEach(departmentID -> {
                    List<StudentGrade> list = departmentRecordMapP.get(departmentID);
                    double departmentGpa = list.stream().parallel().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    departmentGpa = departmentGpa * 100;
                    departmentGpa = Math.round(departmentGpa);
                    departmentGpa = departmentGpa / 100;
                    dGPAmP.put(departmentID, departmentGpa);
                });

        Map<String, List<StudentGrade>> courseRecordMapP =
                studentGradeList.stream().parallel().collect(Collectors.groupingBy(sg -> sg.getCourseNumber()));

        Map<String, Double> cGPAmP = new HashMap<>();

        courseRecordMap.keySet().stream().parallel().
                forEach(courseNumber -> {
                    List<StudentGrade> list = courseRecordMapP.get(courseNumber);
                    double courseGPA = list.stream().parallel().
                            mapToDouble(record -> Double.parseDouble(record.getStudentGrade())).
                            average().getAsDouble();
                    courseGPA = courseGPA * 100;
                    courseGPA = Math.round(courseGPA);
                    courseGPA = courseGPA / 100;
                    cGPAmP.put(courseNumber, courseGPA);
                });

        endTime = new Date().getTime();
        System.out.println("Parallel Streams Execution Time: " + (endTime - startTime) + " milliseconds");

        /** Parallel computation with stream ends **/

        System.out.println();
    }
}
