		subNodes.add(node);
	}

	private Object createObject(String className) {
		Assert.isNotNull(className);
		try {
			Class<?> cl = Class.forName(className);
