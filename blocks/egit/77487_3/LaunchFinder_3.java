	public static ILaunchConfiguration getRunningLaunchConfiguration(
			final Collection<Repository> repositories,
			IProgressMonitor monitor) {
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
