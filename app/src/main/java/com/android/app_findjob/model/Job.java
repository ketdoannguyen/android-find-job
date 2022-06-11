package com.android.app_findjob.model;

public class Job  {
    private int id , employerID = 0;
    private String name,skill,salary,address,city,vacancies,time = "";
    private describe describe ;
    private request request ;
    private Employer employer ;

    private String status,timeApply = "" ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public com.android.app_findjob.model.describe getDescribe() {
        return describe;
    }

    public void setDescribe(com.android.app_findjob.model.describe describe) {
        this.describe = describe;
    }

    public com.android.app_findjob.model.request getRequest() {
        return request;
    }

    public void setRequest(com.android.app_findjob.model.request request) {
        this.request = request;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeApply() {
        return timeApply;
    }

    public void setTimeApply(String timeApply) {
        this.timeApply = timeApply;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", employerID=" + employerID +
                ", name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                ", salary='" + salary + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", vacancies='" + vacancies + '\'' +
                ", time='" + time + '\'' +
                ", describe=" + describe +
                ", request=" + request +
                ", employer=" + employer +
                '}';
    }
}
