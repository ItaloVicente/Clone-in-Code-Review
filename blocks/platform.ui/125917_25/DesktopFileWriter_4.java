
	public static List<String> getMinimalDesktopFileContent(String eclipseExecutableLocation) {
		String executable = eclipseExecutableLocation.replaceAll(" ", "\\\\ "); //$NON-NLS-1$ //$NON-NLS-2$
		return Arrays.asList(//
				"[Desktop Entry]", // //$NON-NLS-1$
				"Exec=" + executable, // //$NON-NLS-1$
				"NoDisplay=true", // //$NON-NLS-1$
				"Type=Application" //$NON-NLS-1$
		);
	}

