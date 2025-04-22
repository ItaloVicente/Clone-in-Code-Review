	public boolean isWindows() {
		String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return getProperty("os.name");
					}
				});
		return osDotName.startsWith("Windows");
	}

