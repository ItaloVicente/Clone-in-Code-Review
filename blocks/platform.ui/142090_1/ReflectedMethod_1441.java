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
