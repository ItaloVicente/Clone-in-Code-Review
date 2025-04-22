		};
		job.setUser(true);
		job.belongsTo(CLONE_JOB_FAMILY);
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
