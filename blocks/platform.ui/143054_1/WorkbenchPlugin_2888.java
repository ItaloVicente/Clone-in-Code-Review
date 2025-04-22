		if (operationSupport != null) {
			operationSupport.dispose();
			operationSupport = null;
		}

		DEBUG = false;

	}

	public static Object createExtension(final IConfigurationElement element, final String classAttribute)
			throws CoreException {
		try {
