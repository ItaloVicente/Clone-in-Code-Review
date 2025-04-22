		if (cloneDestination.isImportProjects()) {
			final IWorkingSet[] sets = cloneDestination.getWorkingSets();
			op.addPostCloneTask(new PostCloneTask() {
				public void execute(Repository repository,
						IProgressMonitor monitor) throws CoreException {
					importProjects(repository, sets);
				}
			});
		}

