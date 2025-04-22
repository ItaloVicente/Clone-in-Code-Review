
	public String getExecutableLocation() {
		String executableLocation = properties.get(KEY_EXEC);
		return executableLocation.replace(EXEC_URI_PLACEHOLDER, ""); //$NON-NLS-1$ // cut uri placeholder
	}
}
