package com.crm.model;

import com.crm.dao.DatabaseEntity;


public class Customer implements DatabaseEntity{

    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private int creditPoints;
    private FullName fullname;

    @Override
    public Object[] toObjectArray(){
        return new Object[] { customerId, firstName, lastName, email, phone, address, creditPoints };
    }

    public Customer() {}

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


	public int getCreditPoints() {
		return creditPoints;
	}

	public void setCreditPoints(int creditPoints) {
		this.creditPoints = creditPoints;
	}


	public FullName getFullname(){
        return fullname;
    }

    public void setFullname(FullName fullname){
        this.fullname = fullname;
    }

    @Override
    public String toString(){
        return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                                        + email + ", phone=" + phone + ", address=" + address + ", creditPoints="
                                        + creditPoints + ", fullname=" + fullname + "]";
    }

    public static class FullName{
        private String firstName;
        private String lastName;

        public FullName(){
        }

        public FullName(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName(){
            return firstName;
        }

        public void setFirstName(String firstName){
            this.firstName = firstName;
        }

        public String getLastName(){
            return lastName;
        }

        public void setLastName(String lastName){
            this.lastName = lastName;
        }

        @Override
        public String toString(){
            return "FullName [firstName=" + firstName + ", lastName=" + lastName + "]";
        }
    }

}

