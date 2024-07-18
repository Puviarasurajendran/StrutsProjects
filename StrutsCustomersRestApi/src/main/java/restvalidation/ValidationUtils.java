package restvalidation;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zoho.util.ResponseCode;
import com.zoho.util.StandardResponse;

import restaction.CustomersRestAction;
import restmodel.Customer;

public class ValidationUtils {

	private static StandardResponse stdResponse;

	 public static boolean validation(Customer c) throws CustomValidateException {
		 StandardResponse stdRes= new StandardResponse();
		 LinkedHashMap details= new LinkedHashMap();
		 boolean flag = true;
		 if(c.getFirstName()==null ) {
			 flag = false;
			 details.put("key", "FirstName");
	         details.put("Resolution", "This key Mandatory & can't accept null value ");
			 CustomersRestAction.setStatusCode(400);
            throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND,details,"Please check the Details to resolve");

		 }
		 else if(c.getFirstName().length()>20) {
	            flag = false;
	            details.put("key", "FirstName");
		        details.put("Resolution", "This key length must be within 20 character");
	            CustomersRestAction.setStatusCode(400);
	       throw new CustomValidateException(ResponseCode.INVALID_DATA,details, "Please check the Details to resolve");
		 }
		 else if(c.getAge()<1 ) {
	            stdRes.setCode(ResponseCode.MANDATORY_NOT_FOUND);
	            details.put("key", "age");
	            details.put("Resolution", "This key Mandatory & can't accept null value ");
	            flag = false;
	            CustomersRestAction.setStatusCode(400);
	            throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND,details, "Please check the Details to resolve ");
			 }

		 else if (c.getAge()< 18 || c.getAge() > 60){
			 stdRes.setCode(ResponseCode.INVALID_DATA);
			 details.put("key", "age");
			 details.put("Resolution", "This key value must be 18-60");
	            flag = false;
	            CustomersRestAction.setStatusCode(400);
	 	       throw new CustomValidateException(ResponseCode.INVALID_DATA,details, "Please check the Details to resolve ");
		 }
		 else if(!isValidEmail(c.getEmail())) {
			 stdRes.setCode(ResponseCode.INVALID_DATA);
			 details.put("key", "email");
			 details.put("Resolution", "This key value doesn't exits email format");
	            flag = false;
	            CustomersRestAction.setStatusCode(400);
	 	       throw new CustomValidateException(ResponseCode.INVALID_DATA,details, "Please check the Details to resolve ");
		 }
		 else if(c.getEmail().length()>20) {
			 stdRes.setCode(ResponseCode.INVALID_DATA);
			 details.put("key", "email");
			 details.put("Resolution", "This key length must be within 20 character");
			 flag = false;
			   CustomersRestAction.setStatusCode(400);
	 	       throw new CustomValidateException(ResponseCode.INVALID_DATA,details, "Please check the Details to resolve ");

		 }
		 System.out.println("Flag of validation :"+flag);
		 return flag;
	 }

	       private static final String EMAIL_PATTERN =
		        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
		        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		    public static boolean isValidEmail(String email) {
		        Matcher matcher = pattern.matcher(email);
		        return matcher.matches();
		    }

			public static StandardResponse getStdResponse() {
				return stdResponse;
			}

			public static void setStdResponse(StandardResponse stdResponse) {
				ValidationUtils.stdResponse = stdResponse;
			}

}
