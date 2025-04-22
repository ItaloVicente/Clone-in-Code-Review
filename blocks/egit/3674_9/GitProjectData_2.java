		Job job = new Job(CoreText.GitProjectData_repositoryChangedJobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				RepositoryChangeListener[] listeners = getRepositoryChangeListeners();
				monitor.beginTask(
						CoreText.GitProjectData_repositoryChangedTaskName,
						listeners.length);

				for (RepositoryChangeListener listener : listeners) {
					listener.repositoryChanged(which);
					monitor.worked(1);
				}

				monitor.done();

				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.REPOSITORY_CHANGED.equals(family))
					return true;

				return super.belongsTo(family);
			}
		};

		job.schedule();
