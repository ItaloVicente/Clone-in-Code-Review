		if (subscriber == null) {
			try {
				Job.getJobManager().join(GitRepositoryProviderType.class,
						new NullProgressMonitor());
			} catch (InterruptedException e) {
				throw new IllegalStateException(
						"Subscriber initialization aborted"); //$NON-NLS-1$
			}
		}
