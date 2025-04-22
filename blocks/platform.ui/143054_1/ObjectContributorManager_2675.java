	}

	protected List computeCombinedOrder(Class inputClass) {
		List result = new ArrayList(4);
		Class clazz = inputClass;
		while (clazz != null) {
			result.add(clazz);
			Class[] interfaces = clazz.getInterfaces();
			for (Class currentInterface : interfaces) {
				result.add(currentInterface);
			}
			clazz = clazz.getSuperclass();
		}
		return result;
	}
