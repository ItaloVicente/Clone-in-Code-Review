		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		File rootFile = root.getRawLocation().toFile();
		File ignoreFile = new File(rootFile, Constants.GITIGNORE_FILENAME);
		if (ignoreFile.exists()) {
			if (!ignoreFile.delete())
				throw new IOException(ignoreFile + " in use or undeletable");
			assert !ignoreFile.exists();
		}
