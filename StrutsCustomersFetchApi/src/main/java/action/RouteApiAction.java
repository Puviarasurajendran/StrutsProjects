package action;

import com.opensymphony.xwork2.ActionSupport;

public class RouteApiAction extends ActionSupport{

//	private List<Customer> customers;
//	private String message="";
//	HttpServletRequest request= ServletActionContext.getRequest();
//	HttpServletResponse response= ServletActionContext.getResponse();
//
//    @Override
//    public String execute() throws Exception {
//
//     System.out.println("Inside excute method in RouteApiAction in StrutsCustomerFetchApi class");
//     return customerHandler();
//    }
//
//    public String customerHandler(){
//        String pathInfo = request.getRequestURI();
//        String version = getVersionFromPath(pathInfo);
//        System.out.println("version StrutsCustomerFetchApi class :"+version);
//
//        String actionName = getActionNameForVersion(version);
//        if (actionName == null) {
//            message = "Unknown API version!";
//            return SUCCESS;
//        }
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("namespace", "/");
//
//        CustomerProxyFactory factory= new CustomerProxyFactory();
//
//        ActionProxy proxy = factory.createActionProxy("/api/*", actionName, "getAllCustomers", params, true, false);
//        try {
//			proxy.execute();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        customers =  (List<Customer>) ActionContext.getContext().getValueStack().findValue("customers");
//        return SUCCESS;
//
//    }
//    private String getVersionFromPath(String path) {
//        if (path != null) {
//            if (path.contains("/v1/")) {
//                return "v1";
//            } else if (path.contains("/v2/")) {
//                return "v2";
//            }
//        }
//        return null;
//    }
//
//    private String getActionNameForVersion(String version) {
//        if ("v1".equals(version)) {
//            return "handler.CustomerV1Action";
//        } else if ("v2".equals(version)) {
//            return "handler.CustomerV2Action";
//        }
//        return null;
//    }
//
//	public List<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(List<Customer> customers) {
//		this.customers = customers;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
}