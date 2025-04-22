		setupDefaultRepositoryFolder();
	}

	private void setupDefaultRepositoryFolder() {
		String defaultFolder = getPreferenceStore().getString(
				UIPreferences.DEFAULT_REPO_DIR);
		File testFile = new File(defaultFolder);
		if (!testFile.exists()) {
			if (!testFile.mkdirs())
				logError(NLS.bind(UIText.Activator_DefaultRepoFolderNotCreated,
						testFile.getPath()), null);
		} else if (testFile.isFile())
			logError(
					NLS.bind(UIText.Activator_DefaultRepoFolderIsFile,
							testFile.getPath()), null);
