
		Collection<ProjectFolder> subfolders = folder.getSubfolders();
		for (ProjectFolder projectFolder : subfolders) {
			selectProjectRecordsInFolder(projectFolder);
			if(hasValidProjects(projectFolder))
				projectsList.setChecked(projectFolder, true);
		}

