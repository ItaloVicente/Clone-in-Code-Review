	protected static boolean disableTestsBug493357 = false;

	private static final String GTK_VERSION_PROPERTY = "org.eclipse.swt.internal.gtk.version";
	static {
		String value = System.getProperty(GTK_VERSION_PROPERTY);
		if (value != null) {
			if (value.startsWith("3")) {
				disableTestsBug493357 = true;
			}
		}

	}

