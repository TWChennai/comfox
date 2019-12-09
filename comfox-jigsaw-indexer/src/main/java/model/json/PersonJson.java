package model.json;

import model.*;

public class PersonJson {
    public String employeeId;
    public String loginName;
    public String preferredName;
    public Gender gender;
    public Picture picture;
    public Role role;
    public Grade grade;
    public Department department;
    public String hireDate;
    public String totalExperience;
    public String twExperience;
    public Boolean assignable;
    public HomeOffice homeOffice;
    public WorkingOffice workingOffice;
    public String projectPreferences;
    public String longTermGoal;
    public TravelPreferences travelPreferences;

    public class Picture {
        public String url;
    }

    public class Role {
        public String name;
    }

    public class Grade {
        public String name;
    }

    public class Department {
        public String name;
    }

    public class HomeOffice {
        public String name;
    }

    public class WorkingOffice {
        public String name;
    }
}
