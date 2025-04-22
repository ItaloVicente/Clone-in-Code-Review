							};

							try {
								ResourcesPlugin.getWorkspace().run(
										wsr,
										ResourcesPlugin.getWorkspace()
												.getRoot(),
										IWorkspace.AVOID_UPDATE,
										monitor);
								scheduleRefresh();
							} catch (CoreException e1) {
								return e1.getStatus();
