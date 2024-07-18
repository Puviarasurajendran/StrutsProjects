package restdao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import restmodel.Customer;
import restvalidation.CustomValidateException;

@Component
public interface CustomersInterface {

	abstract List<Customer> getAllCustomers();
	abstract Map<String,Object> getCustomerByID_v1(int id);
	abstract void createCustomer_v1(Customer c) throws CustomValidateException;
	abstract void updateCustomer_v1(Customer c) throws CustomValidateException;
	abstract void deleteCustomer_v1(int id) throws SQLException;
    abstract List<Map<String, Object>> getTotalCutomers_v1();

}
