package org.eclipse.jface.examples.databinding.ducks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class RelaxedDuckType extends DuckType implements InvocationHandler {

	public static Object implement(Class interfaceToImplement, Object object) {
		return Proxy.newProxyInstance(interfaceToImplement.getClassLoader(),
				new Class[] {interfaceToImplement}, new RelaxedDuckType(object));
	}

    public static boolean includes(Object object, String method, Class[] args) {
        try {
            object.getClass().getMethod(method, args);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    private static final HashMap NULL_VALUES = new HashMap(); {
        NULL_VALUES.put(Boolean.TYPE, Boolean.FALSE);
        NULL_VALUES.put(Integer.TYPE, new Integer(0));
        NULL_VALUES.put(Float.TYPE, new Float(0));
        NULL_VALUES.put(Long.TYPE, new Long(0));
        NULL_VALUES.put(Double.TYPE, new Double(0));
        NULL_VALUES.put(Character.TYPE, new Character(' '));
    }

	protected RelaxedDuckType(Object object) {
		super(object);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Method realMethod = objectClass.getMethod(method.getName(), method.getParameterTypes());
            return realMethod.invoke(object, args);
		} catch (NoSuchMethodException e) {
			return NULL_VALUES.get(method.getReturnType());
		} catch (Throwable t) {
			throw t;
		}
	}

}
