		final ILaunchConfiguration[] lc = new ILaunchConfiguration[1];
		try {
			ModalContext.run(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {

					Set<IProject> projects = new HashSet<IProject>(Arrays
							.asList(ProjectUtil.getProjects(repository)));

					ILaunchManager launchManager = DebugPlugin.getDefault()
							.getLaunchManager();
					ILaunch[] launches = launchManager.getLaunches();
					for (ILaunch launch : launches) {
						if (launch.isTerminated())
							continue;
						ISourceLocator locator = launch.getSourceLocator();
						if (locator instanceof ISourceLookupDirector) {
							ISourceLookupDirector director = (ISourceLookupDirector) locator;
							ISourceContainer[] containers = director
									.getSourceContainers();
							if (isAnyProjectInSourceContainers(containers,
									projects)) {
								lc[0] = launch.getLaunchConfiguration();
								return;
							}
						}
					}
				}
			}, true, new NullProgressMonitor(), getShell().getDisplay());
		} catch (@SuppressWarnings("unused") InvocationTargetException e) {
		} catch (@SuppressWarnings("unused") InterruptedException e) {
