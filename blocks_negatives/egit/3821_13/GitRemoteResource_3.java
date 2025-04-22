	@Override
	protected void fetchContents(IProgressMonitor monitor) throws TeamException {
		CommitBlobStorage content = new CommitBlobStorage(repo, path, objectId, revCommit);
		try {
			setContents(content.getContents(), monitor);
		} catch (CoreException e) {
			Activator.error("", e); //$NON-NLS-1$
		}
	}

