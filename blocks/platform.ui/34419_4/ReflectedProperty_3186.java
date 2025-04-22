package org.eclipse.jface.examples.databinding.ducks;

import java.lang.reflect.Method;

public class ReflectedMethod {

    private Object subject;
    private Method method;

    public ReflectedMethod(Object subject, String methodName, Class[] paramTypes) {
        this.subject = subject;
        method = null;
        try {
        	method = subject.getClass().getMethod(methodName, paramTypes);
        } catch (Exception e) {
        	System.out.println(e);
        }
    }

    public boolean exists() {
        return method != null;
    }

    public Object invoke(Object[] params) {
        if (method == null)
            return null;
        try {
        	if (!method.isAccessible()) {
        		method.setAccessible(true);
        	}
        	return method.invoke(subject, params);
        } catch (Exception e) {
            return null;
        }
    }

	public Class getType() {
		return method.getReturnType();
	}
}


