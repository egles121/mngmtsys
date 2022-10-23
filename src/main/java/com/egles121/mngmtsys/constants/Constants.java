package com.egles121.mngmtsys.constants;

public class Constants {

    public static final String EMPLOYEE_NOT_FOUND = "The employee with provided id does not exist";
    public static final String DEPARTMENT_EMPTY = "The department name value cannot be empty";
    public static final String DEPARTMENT_NOT_FOUND = "The specified department does not exist";
    public static final String PATTERN_FORMAT = "yyyy-MM-dd";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
