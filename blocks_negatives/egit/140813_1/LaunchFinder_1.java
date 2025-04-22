		SubMonitor progress = SubMonitor.convert(monitor, 1);
		final ILaunchConfiguration[] result = { null };
		IRunnableWithProgress operation = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor m)
					throws InvocationTargetException, InterruptedException {
				Set<IProject> projects = new HashSet<>();
				for (Repository repository : repositories) {
					projects.addAll(
							Arrays.asList(ProjectUtil.getProjects(repository)));
				}
				result[0] = findLaunch(projects, m);
			}
		};
		try {
			if (ModalContext.isModalContextThread(Thread.currentThread())) {
				operation.run(progress);
			} else {
				ModalContext.run(operation, true, progress,
						PlatformUI.getWorkbench().getDisplay());
			}
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
		return result[0];
	}

	private static ILaunchConfiguration findLaunch(Set<IProject> projects,
			IProgressMonitor monitor) {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunch[] launches = launchManager.getLaunches();
		SubMonitor progress = SubMonitor.convert(monitor,
				UIText.LaunchFinder_SearchLaunchConfiguration,
				launches.length);
		for (ILaunch launch : launches) {
			if (progress.isCanceled()) {
				break;
			}
			if (launch.isTerminated()) {
				progress.worked(1);
				continue;
			}
			ISourceLocator locator = launch.getSourceLocator();
			if (locator instanceof ISourceLookupDirector) {
				ISourceLookupDirector director = (ISourceLookupDirector) locator;
				ISourceContainer[] containers = director.getSourceContainers();
				if (isAnyProjectInSourceContainers(containers, projects,
						progress.newChild(1))) {
					return launch.getLaunchConfiguration();
				}
			} else {
				progress.worked(1);
			}
		}
		return null;
	}

	private static boolean isAnyProjectInSourceContainers(
			ISourceContainer[] containers, Set<IProject> projects,
			IProgressMonitor monitor) {
		if (containers == null) {
			return false;
		}
		SubMonitor progress = SubMonitor.convert(monitor, containers.length);
		for (ISourceContainer container : containers) {
			if (progress.isCanceled()) {
				break;
			}
			if (container instanceof ProjectSourceContainer) {
				ProjectSourceContainer projectContainer = (ProjectSourceContainer) container;
				if (projects.contains(projectContainer.getProject())) {
					progress.worked(1);
					return true;
				}
			}
			try {
				boolean found = isAnyProjectInSourceContainers(
						container.getSourceContainers(), projects,
						progress.newChild(1));
				if (found) {
					return true;
				}
			} catch (CoreException e) {
			}
		}
		return false;
