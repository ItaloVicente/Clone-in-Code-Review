	public IProject[] getAllNestedProjects(IContainer container) {
		if (container instanceof IWorkspaceRoot) {
			IWorkspaceRoot root = (IWorkspaceRoot) container;
			return root.getProjects();
		}
		Set<IProject> res = new HashSet<>();
		IPath containerLocation = container.getLocation();
		IPath projectLocation = container.getProject().getLocation();
		if (containerLocation == null || projectLocation == null) {
			return res.toArray(new IProject[res.size()]);
		}
		synchronized (locationsToProjects) {
			for (Entry<IPath, IProject> entry : locationsToProjects.tailMap(containerLocation).entrySet()) {
				if (entry.getValue().equals(container.getProject())) {
				} else if (containerLocation.isPrefixOf(entry.getKey())) {
					res.add(entry.getValue());
				} else { // moved to another branch, not worth continuing
					break;
				}
			}
		}
		return res.toArray(new IProject[res.size()]);
	}

