
			for (Iterator iterator = projects.keySet().iterator(); iterator.hasNext();) {
				IProject project = (IProject) iterator.next();
				monitor.setTaskName(NLS.bind(
						CoreText.ConnectProviderOperation_ConnectingProject,
						project.getName()));
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),

				RepositoryFinder finder = new RepositoryFinder(project);
				finder.setFindInChildren(false);
				Collection<RepositoryMapping> repos = finder.find(new SubProgressMonitor(monitor, 40));
				File suggestedRepo = projects.get(project);
				RepositoryMapping actualMapping= findActualRepository(repos, suggestedRepo);
				if (actualMapping != null) {
					GitProjectData projectData = new GitProjectData(project);
					try {
						projectData.setRepositoryMappings(Arrays.asList(actualMapping));
						projectData.store();
						GitProjectData.add(project, projectData);
					} catch (CoreException ce) {
						try {
							GitProjectData.delete(project);
						} catch (IOException e) {
							MultiStatus status = new MultiStatus(
									Activator.getPluginId(), IStatus.ERROR,
									e.getMessage(), e);
							status.add(new Status(IStatus.ERROR, Activator
									.getPluginId(), ce.getMessage(), ce));
							throw new CoreException(status);
						}
						throw ce;
					} catch (RuntimeException ce) {
						try {
							GitProjectData.delete(project);
						} catch (IOException e) {
							MultiStatus status = new MultiStatus(
									Activator.getPluginId(), IStatus.ERROR,
									e.getMessage(), e);
							status.add(new Status(IStatus.ERROR, Activator
									.getPluginId(), ce.getMessage(), ce));
							throw new CoreException(status);
					}
						throw ce;
					}
					RepositoryProvider
							.map(project, GitProvider.class.getName());
					autoIgnoreDerivedResources(project, monitor);
					project.refreshLocal(IResource.DEPTH_INFINITE,
							new SubProgressMonitor(monitor, 50));
					monitor.worked(10);
				} else {
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.CORE.getLocation(),
										+ project);
					monitor.worked(60);
				}
