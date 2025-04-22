
		return username;
	}

	private static String getDefaultUserName() {
		String username = system().getProperty(Constants.OS_USER_NAME_KEY);
		if (username == null)
