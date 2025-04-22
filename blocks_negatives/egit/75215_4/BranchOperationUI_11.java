				@Override
				public void run(IProgressMonitor m)
						throws InvocationTargetException, InterruptedException {

					Set<IProject> projects = new HashSet<>(Arrays
							.asList(ProjectUtil.getProjects(repository)));

					ILaunchManager launchManager = DebugPlugin.getDefault()
							.getLaunchManager();
					ILaunch[] launches = launchManager.getLaunches();
					m.beginTask(
							UIText.BranchOperationUI_SearchLaunchConfiguration,
							launches.length);
					for (ILaunch launch : launches) {
						m.worked(1);
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
			}, true, monitor, Display.getDefault());
