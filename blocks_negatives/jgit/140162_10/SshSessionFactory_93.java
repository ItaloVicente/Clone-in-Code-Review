		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				return SystemReader.getInstance()
						.getProperty(Constants.OS_USER_NAME_KEY);
			}
		});
