package com.example.unirun.dto;

/**
 * @author wzh
 */
public class NewRecordModel {
    private Integer runDistance;
    private Integer runTime;
    private String mapChoice;
    private String token;
    private String userId;
    private String studentName;
    private String schoolId;

    public Integer getRunDistance() {
        return runDistance;
    }

    public void setRunDistance(Integer runDistance) {
        this.runDistance = runDistance;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public String getMapChoice() {
        return mapChoice;
    }

    public void setMapChoice(String mapChoice) {
        this.mapChoice = mapChoice;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "NewRecordModel{" +
                "runDistance='" + runDistance + '\'' +
                ", runTime='" + runTime + '\'' +
                ", mapChoice='" + mapChoice + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
