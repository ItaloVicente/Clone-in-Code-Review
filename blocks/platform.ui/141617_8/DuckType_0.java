		for (Method method : methods) {
			try {
				candclass.getMethod(method.getName(), method.getParameterTypes());
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		return true;
	}
