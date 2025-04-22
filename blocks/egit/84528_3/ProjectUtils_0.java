				IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
						.getWorkingSetManager();
				actMonitor.beginTask("", projectsToCreate.size() * 2 + 1); //$NON-NLS-1$
				if (actMonitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				Map<IProject, File> projectsToConnect = new HashMap<>();
				SubMonitor progress = SubMonitor.convert(actMonitor,
						projectsToCreate.size() * 2 + 1);
				for (ProjectRecord projectRecord : projectsToCreate) {
					if (actMonitor.isCanceled()) {
