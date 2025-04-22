	private boolean runsOnWindows() {
		final String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return System.getProperty("os.name");
					}
				});
		return osDotName != null
				&& StringUtils.toLowerCase(osDotName).indexOf("windows") != -1;
	}

