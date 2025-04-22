	private void registerBuiltinFilter(String filterClassName) {
		Class<?> filter;
		try {
			filter = Class.forName(filterClassName); // $NON-NLS-1$
			if (filter != null) {
				filter.getMethod("register").invoke(null); //$NON-NLS-1$
			}
		} catch (ClassNotFoundException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
		}
	}

