	/**
	 * @return The default repository directory as configured in the
	 *         preferences, with variables substituted. An empty string if there
	 *         was an error during substitution.
	 */
	public static String getDefaultRepositoryDir() {
		String dir = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DEFAULT_REPO_DIR);
		IStringVariableManager manager = VariablesPlugin.getDefault()
				.getStringVariableManager();
		try {
			return manager.performStringSubstitution(dir);
		} catch (CoreException e) {
		}
	}

