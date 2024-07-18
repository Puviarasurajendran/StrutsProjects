

package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dao.CustomersDAO;
import model.Customer;

public class CustomersAction extends ActionSupport implements ModelDriven<Customer>{

    private HttpServletRequest request;
	private int cid;
    private Customer customer = new Customer();
//  private Customer customer;
    private List<Customer> customers;
    private CustomersDAO customersDAO = new CustomersDAO();


    public String getCustomerById() {
        customer = customersDAO.getCustomerById(cid);
        if (customer != null) {
            return SUCCESS;
        } else {
            addActionError("Customer not found");
            return ERROR;
        }
    }

    public String getAllCustomers() {
    	 System.out.println("Inside getAllCustomers in strutsCustomerFetchAPI ");
        customers = customersDAO.getAllCustomers();
        System.out.println(customers);
        return SUCCESS;
    }


    public String createCustomer() {
    	  try {
//    		   String json = IOUtils.toString(request.getInputStream());
//               Customer customer = new Gson().fromJson(json, Customer.class);
//               System.out.println("Customer Name: " + customer);
//    		  Customer cus= new Customer(8, "Veera", "Kumar", "veera@zoho.com", 27, "UK");

    	        System.out.println("Customer Name: " + customer.toString());
    	        customersDAO.createCustomer(customer);
    	        return SUCCESS;
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        addActionError("Failed to create customer");
    	        return ERROR;
    	    }
    }

    public String updateCustomer() {
    	System.out.println("Update Customer :"+customer);
        customersDAO.updateCustomer(customer);
        return SUCCESS;
    }


    public String deleteCustomer() {
    	System.out.println("Enter into Delete method :"+cid);
        customersDAO.deleteCustomer(cid);
        return SUCCESS;
    }

    public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

	@Override
	public Customer getModel() {
		return customer;
	}


//    private ActionProxy getActionProxy(String actionName, Map<String, Object> params) throws Exception {
//
//        return ActionProxyFactory.getFactory().createActionProxy("/", actionName, params, true, false);
//    }

//	@Override
//	public void setServletRequest(HttpServletRequest resquest) {
//		this.request=resquest;
//	}

}



