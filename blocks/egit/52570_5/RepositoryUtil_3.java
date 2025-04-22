	@NonNull
	public static String getDefaultRepositoryDir() {
		String key = GitCorePreferences.core_defaultRepositoryDir;
		String dir = migrateRepoRootPreference();
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		if (dir == null) {
			dir = p.get(key, getDefaultDefaultRepositoryDir());
		} else {
			p.put(key, dir);
		}
		IStringVariableManager manager = VariablesPlugin.getDefault()
				.getStringVariableManager();
		String result;
		try {
			result = manager.performStringSubstitution(dir);
		} catch (CoreException e) {
			result = ""; //$NON-NLS-1$
		}
		if (result == null || result.isEmpty()) {
			result = ResourcesPlugin.getWorkspace().getRoot().getRawLocation()
					.toOSString();
		}
		return result;
	}

	@NonNull
	static String getDefaultDefaultRepositoryDir() {
		return new File(FS.DETECTED.userHome(), "git").getPath(); //$NON-NLS-1$
	}

	@Nullable
	private static String migrateRepoRootPreference() {
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode("org.eclipse.egit.ui"); //$NON-NLS-1$
		String deprecatedUiKey = "default_repository_dir"; //$NON-NLS-1$
		String value = p.get(deprecatedUiKey, null);
		if (value != null && value.isEmpty()) {
			value = null;
		}
		if (value != null) {
			p.remove(deprecatedUiKey);
		}
		return value;
	}

