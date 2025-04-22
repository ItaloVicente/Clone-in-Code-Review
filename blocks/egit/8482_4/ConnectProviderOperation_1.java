	private void autoIgnoreTopLevelDerivedResources(IProject project, IProgressMonitor monitor) throws CoreException {
		List<IPath> paths = findDerivedResources(project);
		if (paths.size() > 0) {
			IgnoreOperation ignoreOp = new IgnoreOperation(paths);
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
			ignoreOp.execute(subMonitor);
		}
	}

	private List<IPath> findDerivedResources(IProject project)
			throws CoreException {
		List<IPath> derived = new ArrayList<IPath>();
		IResource[] members = project.members(IContainer.INCLUDE_HIDDEN | IContainer.INCLUDE_TEAM_PRIVATE_MEMBERS);
		for (IResource r : members) {
			if (r.isDerived())
				derived.add(r.getLocation());
		}
		return derived;
	}

