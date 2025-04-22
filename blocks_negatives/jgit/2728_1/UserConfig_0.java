		if (email == null) {
			String username = system().getProperty(Constants.OS_USER_NAME_KEY);
			if (username == null){
				username = Constants.UNKNOWN_USER_DEFAULT;
			}
			email = username + "@" + system().getHostname();
		}

