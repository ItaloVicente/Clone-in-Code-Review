
		SubMonitor progress = SubMonitor.convert(monitor, 8);
		progress.setTaskName(
				UIText.WizardProjectsImportPage_CreateProjectsTask);
		project.create(record.getProjectDescription(), progress.newChild(3));
		project.open(IResource.BACKGROUND_REFRESH, progress.newChild(5));
		return project;
