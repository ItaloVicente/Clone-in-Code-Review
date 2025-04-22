	private static final boolean runsOnWindows;

	static {
		final String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return System.getProperty("os.name");
					}
				});
		runsOnWindows = osDotName != null
				&& StringUtils.toLowerCase(osDotName).indexOf("windows") != -1;
	}

