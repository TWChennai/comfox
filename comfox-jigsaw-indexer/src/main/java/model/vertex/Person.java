package model.vertex;

import model.*;

public class Person {
    private String employeeId;
    private String loginName;
    private String preferredName;
    private Gender gender;
    private String pictureUrl;
    private String role;
    private String grade;
    private String department;
    private String hireDate;
    private String totalExperience;
    private String twExperience;
    private Boolean assignable;
    private String homeOffice;
    private String workingOffice;
    private String projectPreferences;
    private String longTermGoal;
    private TravelPreferences travelPreferences;

    public Person(String employeeId, String loginName, String preferredName, Gender gender, String pictureUrl, String role,
                  String grade, String department, String hireDate, String totalExperience, String twExperience,
                  Boolean assignable, String homeOffice, String workingOffice, String projectPreferences,
                  String longTermGoal, TravelPreferences travelPreferences) {
        this.employeeId = employeeId;
        this.loginName = loginName;
        this.preferredName = preferredName;
        this.gender = gender;
        this.pictureUrl = pictureUrl;
        this.role = role;
        this.grade = grade;
        this.department = department;
        this.hireDate = hireDate;
        this.totalExperience = totalExperience;
        this.twExperience = twExperience;
        this.assignable = assignable;
        this.homeOffice = homeOffice;
        this.workingOffice = workingOffice;
        this.projectPreferences = projectPreferences;
        this.longTermGoal = longTermGoal;
        this.travelPreferences = travelPreferences;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getRole() {
        return role;
    }

    public String getGrade() {
        return grade;
    }

    public String getDepartment() {
        return department;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public String getTwExperience() {
        return twExperience;
    }

    public Boolean getAssignable() {
        return assignable;
    }

    public String getHomeOffice() {
        return homeOffice;
    }

    public String getWorkingOffice() {
        return workingOffice;
    }

    public String getProjectPreferences() {
        return projectPreferences;
    }

    public String getLongTermGoal() {
        return longTermGoal;
    }

    public TravelPreferences getTravelPreferences() {
        return travelPreferences;
    }

    @Override
    public String toString() {
        return "Person{" +
                "employeeId='" + employeeId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", preferredName='" + preferredName + '\'' +
                ", gender=" + gender +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", role='" + role + '\'' +
                ", grade='" + grade + '\'' +
                ", department='" + department + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", totalExperience='" + totalExperience + '\'' +
                ", twExperience='" + twExperience + '\'' +
                ", assignable=" + assignable +
                ", homeOffice='" + homeOffice + '\'' +
                ", workingOffice='" + workingOffice + '\'' +
                ", projectPreferences='" + projectPreferences + '\'' +
                ", longTermGoal='" + longTermGoal + '\'' +
                ", travelPreferences=" + travelPreferences +
                '}';
    }
}
