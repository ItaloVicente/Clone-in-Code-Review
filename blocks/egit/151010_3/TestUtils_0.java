	public static void deleteProject(IProject project) throws CoreException {
		ICoreRunnable runnable = monitor -> {
			for (int i = 0; i < MAX_DELETE_RETRY; i++) {
				try {
					project.delete(
							IResource.FORCE
									| IResource.ALWAYS_DELETE_PROJECT_CONTENT,
							null);
					break;
				} catch (CoreException e) {
					if (i == MAX_DELETE_RETRY - 1) {
						throw e;
					}
					try {
						org.eclipse.egit.core.Activator.logInfo(
								"Sleep before retrying to delete project "
										+ project.getLocationURI());
						Thread.sleep(DELETE_RETRY_DELAY);
					} catch (InterruptedException e1) {
					}
				}
			}
		};

		ResourcesPlugin.getWorkspace().run(runnable, null);
	}

