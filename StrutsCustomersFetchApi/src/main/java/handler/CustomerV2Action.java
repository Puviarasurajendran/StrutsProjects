package handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Customer;


public class CustomerV2Action extends CustomerV1Action{

    @Override
	public String getAllCustomers() {
    	List<Map<String,Object>> customerList= new ArrayList();
        super.setCustomers(dummyData());
        System.out.println(super.getAllCustomers());
        return SUCCESS;
    }


    public List<Customer> dummyData(){

    	List<Customer> customers = new ArrayList<>();
    	 Customer customer1 = new Customer(1, "Alice", "Johnson", "alice.johnson@example.com", 30,"USA");
         Customer customer2 = new Customer(2, "Bob", "Smith", "bob.smith@example.com", 25,"Russia");
         Customer customer3 = new Customer(3, "Charlie", "Brown", "charlie.brown@example.com", 35,"India");
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        return customers;


    }


}
