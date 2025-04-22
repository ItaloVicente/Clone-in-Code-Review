			IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
			String destinationDir;
			File parentDir;
			try {
				destinationDir = manager.performStringSubstitution(defaultRepoDir);
				parentDir = new File(destinationDir);
			} catch (CoreException e) {
				parentDir = null;
			}
			if (parentDir == null || !parentDir.exists() || !parentDir.isDirectory()) {
				parentDir = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile();
