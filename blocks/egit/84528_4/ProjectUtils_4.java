
		SubMonitor progress = SubMonitor.convert(monitor,
				UIText.WizardProjectsImportPage_CreateProjectsTask, 8);
		project.create(record.getProjectDescription(), progress.newChild(3));
		project.open(IResource.BACKGROUND_REFRESH, progress.newChild(5));
		return project;
