	@SuppressWarnings("deprecation")
	static void setAccessible(Method method) {
		if (!method.isAccessible()) {
			method.setAccessible(true);
		}
	}

