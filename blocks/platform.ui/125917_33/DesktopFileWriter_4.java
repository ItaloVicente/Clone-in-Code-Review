
	public static List<String> getMinimalDesktopFileContent(String eclipseExecutableLocation) {
		String executable = escapeSpaces(eclipseExecutableLocation);
		return Arrays.asList(//
				"[Desktop Entry]", // //$NON-NLS-1$
				"Exec=" + executable, // //$NON-NLS-1$
				"NoDisplay=true", // //$NON-NLS-1$
				"Type=Application" //$NON-NLS-1$
		);
	}

	private static String escapeSpaces(String path) {
		return path.replace(" ", "\\ "); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String unescapeSpaces(String path) {
		return path.replace("\\ ", " "); //$NON-NLS-1$ //$NON-NLS-2$
	}

