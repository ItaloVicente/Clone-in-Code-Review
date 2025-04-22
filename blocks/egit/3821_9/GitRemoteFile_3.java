	@Override
	protected void fetchContents(IProgressMonitor monitor) throws TeamException {
		CommitBlobStorage content = new CommitBlobStorage(repo, getPath(),
				getObjectId(), getCommitId());
		try {
			setContents(content.getContents(), monitor);
		} catch (CoreException e) {
			Activator.error("", e); //$NON-NLS-1$
		}
	}

