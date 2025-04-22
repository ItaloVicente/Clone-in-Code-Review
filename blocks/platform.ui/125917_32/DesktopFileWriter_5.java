
	public String getExecutableLocation() {
		String executableLocation = properties.get(KEY_EXEC);
		executableLocation = executableLocation.replace(EXEC_URI_PLACEHOLDER, ""); //$NON-NLS-1$ // cut uri placeholder
		return unescapeSpaces(executableLocation);
	}
}
