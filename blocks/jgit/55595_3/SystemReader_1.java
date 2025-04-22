		if (isMacOS == null) {
			String osDotName = getOsName();
			isMacOS = Boolean.valueOf(
		}
		return isMacOS.booleanValue();
	}

	private String getOsName() {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			public String run() {
			}
		});
