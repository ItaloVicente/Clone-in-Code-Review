	public boolean isMacOS() {
		String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return getProperty("os.name");
					}
				});
		return "Mac OS X".equals(osDotName) || "Darwin".equals(osDotName);
	}

