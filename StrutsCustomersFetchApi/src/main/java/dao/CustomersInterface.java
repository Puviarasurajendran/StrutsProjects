package dao;

import java.util.List;

import model.Customer;

public interface CustomersInterface {


	List<Customer> getAllCustomers();
	Customer getCustomerById(int id);
	void createCustomer(Customer c);
	void updateCustomer(Customer c);
	void deleteCustomer(int id);

}
