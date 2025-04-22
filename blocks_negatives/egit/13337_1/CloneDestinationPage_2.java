			try {
				destinationDir = manager.performStringSubstitution(defaultRepoDir);
				parentDir = new File(destinationDir);
			} catch (CoreException e) {
				parentDir = null;
			}
			if (parentDir == null)
				parentDir = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile();
