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
								return new Status(IStatus.ERROR, Activator
										.getPluginId(), e1.getMessage(), e1);
