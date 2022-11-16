package it.unipi.lsmsd.ecommerce.model;

import java.util.Date;

public class Manager extends RegisteredUser {

    private Date hiredDate;
    private String title;
    private Company company;

    public Manager() {
    }

    public Manager(Long userId){
        this.setId(userId);
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "hiredDate=" + hiredDate +
                ", title='" + title + '\'' +
                ", company=" + company +
                '}';
    }
}
