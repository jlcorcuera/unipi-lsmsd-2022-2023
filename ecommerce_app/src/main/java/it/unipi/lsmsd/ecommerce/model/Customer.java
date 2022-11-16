package it.unipi.lsmsd.ecommerce.model;

import java.util.List;

public class Customer extends RegisteredUser {
    private String address;
    private String country;
    private String phone;
    private List<Order> orders;

    public Customer(){

    }

    public Customer(Long customerId) {
        super.setId(customerId);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }
}
