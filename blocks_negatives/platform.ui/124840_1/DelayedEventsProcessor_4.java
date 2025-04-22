			/*
			 * Helper function to invoke a method on an object.
			 */
			private Object invoke(Object object, String methodName) throws NoSuchMethodException, SecurityException,
					IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				Method method = object.getClass().getMethod(methodName);
				return method.invoke(object);
			}
