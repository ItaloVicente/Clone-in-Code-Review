
	public static List<String> getMinimalDesktopFileContent(String eclipseExecutableLocation) {
		return Arrays.asList(//
				"[Desktop Entry]", // //$NON-NLS-1$
				"Exec=" + eclipseExecutableLocation, // //$NON-NLS-1$
				"NoDisplay=true", // //$NON-NLS-1$
				"Type=Application" //$NON-NLS-1$
		);
	}

