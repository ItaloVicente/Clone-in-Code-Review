		Job.create("Reconfiguring window cache for EGit", (monitor) -> {//$NON-NLS-1$
			try {
				GitProjectData.reconfigureWindowCache();
			} catch (RuntimeException e) {
				logError(CoreText.Activator_ReconfigureWindowCacheError, e);
			}
		}).schedule();
