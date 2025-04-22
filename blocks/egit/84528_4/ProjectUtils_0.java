				IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
						.getWorkingSetManager();
				if (actMonitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				Map<IProject, File> projectsToConnect = new HashMap<>();
				SubMonitor progress = SubMonitor.convert(actMonitor,
						projectsToCreate.size() * 2 + 1);
				for (ProjectRecord projectRecord : projectsToCreate) {
					if (progress.isCanceled()) {
