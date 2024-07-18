package restvalidation;

import com.zoho.util.ResponseCode;

public class CustomValidateException extends RuntimeException {
    private ResponseCode code;
    private Object feild;
    private String message;

    public CustomValidateException(ResponseCode code, Object feild, String message) {
		super(message);
		this.code = code;
		this.feild = feild;
		this.message = message;
	}

	public CustomValidateException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
        return message;
    }

	public ResponseCode getCode() {
		return code;
	}

	public Object getFeild() {
		return feild;
	}

	public void setFeild(Object feild) {
		this.feild = feild;
	}

	@Override
	public String toString() {
		return "CustomException [code=" + code + ", feild=" + feild + ", message=" + message + "]";
	}

}
