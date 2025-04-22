
			private Object invoke(Object object, String methodName) throws NoSuchMethodException, SecurityException,
					IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				Method method = object.getClass().getMethod(methodName);
				return method.invoke(object);
			}

			private Object invoke(Object object, String methodName, Class<?>[] classes, Object[] params)
					throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
					InvocationTargetException {
				Method method = object.getClass().getMethod(methodName, classes);
				return method.invoke(object, params);
			}
