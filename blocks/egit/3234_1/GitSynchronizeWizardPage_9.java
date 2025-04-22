	IProject[] getProjects() {
		Set<IProject> projects = new HashSet<IProject>();
		for (Object checked : treeViewer.getCheckedElements())
			projects.addAll(projMapping.get(checked));

		return projects.toArray(new IProject[projects.size()]);
