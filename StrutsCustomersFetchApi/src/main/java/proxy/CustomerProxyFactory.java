package proxy;

import java.util.Map;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.DefaultActionProxyFactory;

public class CustomerProxyFactory extends DefaultActionProxyFactory {


//	public ActionProxy createProxy(String namespace, String actionName, String methodName,
//			Map<String, Object> extraContext) {
//
//		return createActionProxy(namespace, actionName, extraContext,true, false);
//	}


@Override
public ActionProxy createActionProxy(String namespace, String actionName, String methodName,
		Map<String, Object> extraContext, boolean executeResult, boolean cleanupContext) {

	System.out.println("Inside of createActionProxy for Customers fetch api class");
	return super.createActionProxy(namespace, actionName, methodName, extraContext, executeResult, cleanupContext);
}


}
