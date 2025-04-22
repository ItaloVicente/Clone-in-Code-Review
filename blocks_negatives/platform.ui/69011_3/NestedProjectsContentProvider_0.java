		Set<IProject> nestedProjects = new HashSet<IProject>();
		for (IProject project : container.getWorkspace().getRoot().getProjects()) {
			if (container.getLocation().isPrefixOf(project.getLocation())
					&& project.getLocation().segmentCount() - container.getLocation().segmentCount() == 1) {
				nestedProjects.add(project);
			}
		}
		return nestedProjects.toArray(new IProject[nestedProjects.size()]);
