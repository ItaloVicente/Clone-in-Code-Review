			@Override
			public void run(IProgressMonitor m)
					throws InvocationTargetException, InterruptedException {
				Set<IProject> projects = new HashSet<>(
						Arrays.asList(ProjectUtil.getProjects(repository)));
				result[0] = LaunchFinder.findLaunch(projects, m);
			}
		};
		try {
			if (ModalContext.isModalContextThread(Thread.currentThread())) {
				operation.run(monitor);
			} else {
				ModalContext.run(operation, true, monitor,
						PlatformUI.getWorkbench().getDisplay());
			}
