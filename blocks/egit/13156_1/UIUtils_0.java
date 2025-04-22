	public static String getDefaultRepositoryDir() {
		String dir = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DEFAULT_REPO_DIR);
		IStringVariableManager manager = VariablesPlugin.getDefault()
				.getStringVariableManager();
		try {
			return manager.performStringSubstitution(dir);
		} catch (CoreException e) {
			return ""; //$NON-NLS-1$
		}
	}

