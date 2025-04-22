	public static boolean instanceOf(Class<?> intrface, Object object) {
		final Method[] methods = intrface.getMethods();
		Class<?> candclass = object.getClass();
		for (Method method : methods) {
			try {
				candclass.getMethod(method.getName(), method.getParameterTypes());
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		return true;
	}
