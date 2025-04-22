			/*
			 * Helper function to invoke a method on an object with arguments.
			 */
			private Object invoke(Object object, String methodName, Class<?>[] classes, Object[] params)
					throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
					InvocationTargetException {
				Method method = object.getClass().getMethod(methodName, classes);
				return method.invoke(object, params);
			}
		});
