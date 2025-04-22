
			private Object invoke(Object object, String method) throws NoSuchMethodException, SecurityException,
					IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				Method getDocumentProvider = object.getClass().getMethod(method);
				return getDocumentProvider.invoke(object);
			}

			private Object invoke(Object object, String method, Class[] classes, Object[] params)
					throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
					InvocationTargetException {
				Method getDocumentProvider = object.getClass().getMethod(method, classes);
				return getDocumentProvider.invoke(object, params);
			}
