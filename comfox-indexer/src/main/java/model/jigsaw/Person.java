package model.jigsaw;


public class Person {
    private String employeeId;
    private String loginName;
    private String preferredName;
    private Gender gender;
    private String pictureUrl;
    private Role role;
    private Grade grade;
    private Department department;
    private String hireDate;
    private String totalExperience;
    private String twExperience;
    private Boolean assignable;
    private HomeOffice homeOffice;
    private WorkingOffice workingOffice;
    private String projectPreferences;
    private String longTermGoal;
    private TravelPreferences travelPreferences;

    private class Role {
        private String name;

        public String getName() {
            return name;
        }
    }

    private class Grade {
        private String name;

        public String getName() {
            return name;
        }
    }

    private class Department {
        private String name;

        public String getName() {
            return name;
        }
    }

    private class WorkingOffice {
        private String name;

        public String getName() {
            return name;
        }
    }

    private class HomeOffice {
        private String name;

        public String getName() {
            return name;
        }
    }

    public Person(String employeeId, String loginName, String preferredName, Gender gender, String pictureUrl, Role role, Grade grade, Department department, String hireDate, String totalExperience, String twExperience, Boolean assignable, HomeOffice homeOffice, WorkingOffice workingOffice, String projectPreferences, String longTermGoal, TravelPreferences travelPreferences) {
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
        return role.getName();
    }

    public String getGrade() {
        return grade.getName();
    }

    public String getDepartment() {
        return department.getName();
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
        return homeOffice.getName();
    }

    public String getWorkingOffice() {
        return workingOffice.getName();
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
                ", role=" + role +
                ", homeOffice=" + homeOffice +
                '}';
    }
}
