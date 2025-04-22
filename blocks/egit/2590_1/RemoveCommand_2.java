	private List<IProject> findProjectsToDelete(final List<RepositoryNode> selectedNodes) {
		final List<IProject> projectsToDelete = new ArrayList<IProject>();
		for (RepositoryNode node : selectedNodes) {
			if (node.getRepository().isBare())
				continue;
			File workDir = node.getRepository().getWorkTree();
			final IPath wdPath = new Path(workDir.getAbsolutePath());
			for (IProject prj : ResourcesPlugin.getWorkspace()
					.getRoot().getProjects()) {
				if (wdPath.isPrefixOf(prj.getLocation())) {
					projectsToDelete.add(prj);
				}
			}
		}
		return projectsToDelete;
	}

