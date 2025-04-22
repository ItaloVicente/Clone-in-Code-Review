	public Collection<RepositoryMapping> find(IProgressMonitor m)
			throws CoreException {
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;
		find(monitor, proj);
