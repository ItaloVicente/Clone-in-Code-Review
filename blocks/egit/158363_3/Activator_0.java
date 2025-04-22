		Job createWindowsCacheJob = Job.create("Reconfiguring window cache for EGit", //$NON-NLS-1$
				(monitor) -> {
					try {
						GitProjectData.reconfigureWindowCache();
					} catch (RuntimeException e) {
						logError(CoreText.Activator_ReconfigureWindowCacheError,
								e);
					}
				});
				createWindowsCacheJob.schedule();

