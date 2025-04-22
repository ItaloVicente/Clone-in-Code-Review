	private boolean hasTagsChildren(Repository repo) {
		try {
			if (branchRefs.get(repo) == null) {
				WorkspaceJob job = new WorkspaceJob(
						UIText.RepositoriesViewContentProvider_ReadReferencesJob) {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
							throws CoreException {
						try {
							getRefs(repo, Constants.R_TAGS);
						} catch (IOException e) {
							return Status.CANCEL_STATUS;
						}
						return Status.OK_STATUS;
					}
				};
				job.setSystem(true);
				job.schedule();
				return true;
			}
			return !getRefs(repo, Constants.R_TAGS).isEmpty();
		} catch (IOException e) {
			return true;
		}
	}

