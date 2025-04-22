			monitor
					.beginTask(
							DataTransferMessages.WizardProjectsImportPage_CreateProjectsTask,
							100);
			project.create(record.description, new SubProgressMonitor(monitor,
					30));
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 70));
