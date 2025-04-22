
		try {
			monitor.beginTask(
					UIText.WizardProjectsImportPage_CreateProjectsTask, 100);
			project.create(record.getProjectDescription(),
					new SubProgressMonitor(monitor, 30));
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 50));
			return project;
		} finally {
			monitor.done();
