
			@Override
			public boolean belongsTo(Object family) {
				return CLONE_JOB_FAMILY.equals(family);
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private IStatus executeCloneOperation(final CloneOperation op,
			final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		final RepositoryUtil util = Activator.getDefault()
				.getRepositoryUtil();

		op.run(monitor);
		util.addConfiguredRepository(op.getGitDir());
		return Status.OK_STATUS;
