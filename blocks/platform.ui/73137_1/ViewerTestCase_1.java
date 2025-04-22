		final String GTK_VERSION_PROPERTY = "org.eclipse.swt.internal.gtk.version";
		String value = System.getProperty(GTK_VERSION_PROPERTY);
		if (value != null) {
			if (value.startsWith("3")) {
				disableTestsBug493357 = true;
			}
		}
