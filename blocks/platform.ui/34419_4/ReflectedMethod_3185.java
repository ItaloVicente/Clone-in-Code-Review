package org.eclipse.jface.examples.databinding.ducks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DuckType implements InvocationHandler {

   public static interface Wrapper {
      public Object duckType_GetWrappedValue();
   }

	public static Object implement(Class interfaceToImplement, Object object) {
		return Proxy.newProxyInstance(interfaceToImplement.getClassLoader(),
				new Class[] {interfaceToImplement, Wrapper.class}, new DuckType(object));
	}

    public static boolean instanceOf(Class intrface, Object object) {
        final Method[] methods = intrface.getMethods();
        Class candclass=object.getClass();
        for (int methodidx = 0; methodidx < methods.length; methodidx++) {
            Method method=methods[methodidx];
            try {
                candclass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        return true;
    }

	protected DuckType(Object object) {
		this.object = object;
		this.objectClass = object.getClass();
	}

	protected Object object;
	protected Class objectClass;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (method.getName().equals("equals") && args != null && args.length == 1) {
         return new Boolean(equals(args[0]));
      }
      if (method.getName().equals("hashCode") && args == null) {
         return new Integer(hashCode());
      }
      if (method.getName().equals("duckType_GetWrappedValue") && args == null) {
         return object;
      }
		Method realMethod = objectClass.getMethod(method.getName(), method.getParameterTypes());
      if (!realMethod.isAccessible()) {
         realMethod.setAccessible(true);
      }
		return realMethod.invoke(object, args);
	}

   @Override
public boolean equals(Object obj) {
      if (obj instanceof Wrapper) {
         Wrapper proxy = (Wrapper) obj;
         Object wrappedValue = proxy.duckType_GetWrappedValue();
         return wrappedValue.equals(object);
      }
      return obj == this || super.equals(obj) || object.equals(obj);
   }

   @Override
public int hashCode() {
      return object.hashCode();
   }
}
