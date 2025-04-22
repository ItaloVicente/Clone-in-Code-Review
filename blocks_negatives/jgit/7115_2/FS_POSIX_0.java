
	private static boolean isMacOS() {
		final String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return System.getProperty("os.name");
					}
				});
		return "Mac OS X".equals(osDotName) || "Darwin".equals(osDotName);
	}
