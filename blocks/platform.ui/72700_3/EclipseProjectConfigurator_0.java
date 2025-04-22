		WizardProjectsImportPage.collectProjectFilesFromDirectory(projectFiles, root, visitedDirectories, true,
				monitor);
		Set<File> res = new LinkedHashSet<>();
		for (File projectFile : projectFiles) {
			res.add(projectFile.getParentFile());
		}
