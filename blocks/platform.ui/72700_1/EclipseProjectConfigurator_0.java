		WizardProjectsImportPage.collectProjectFilesFromDirectory(projectFiles, root, visitedDirectories, true,
				monitor);
		Set<File> res = new HashSet<>();
		for (File projectFile : projectFiles) {
			res.add(projectFile.getParentFile());
		}
