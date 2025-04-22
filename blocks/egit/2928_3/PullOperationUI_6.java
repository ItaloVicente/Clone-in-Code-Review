	public void execute(IProgressMonitor monitor) {
		try {
			pullOperation.execute(monitor);
			results.putAll(pullOperation.getResults());
		} catch (CoreException e) {
			if (e.getStatus().getSeverity() == IStatus.CANCEL)
				results.putAll(pullOperation.getResults());
			else
				Activator.handleError(e.getMessage(), e, true);
		}
