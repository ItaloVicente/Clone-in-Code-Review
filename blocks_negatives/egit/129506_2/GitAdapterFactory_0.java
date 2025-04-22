		try {
			IPath repoRelativePath = gitPath.makeRelativeTo(
					new Path(repository.getWorkTree().getAbsolutePath()));
			IProject[] projects = ProjectUtil.getProjectsContaining(repository,
					Collections.singleton(repoRelativePath.toString()));
			if (projects.length > 0) {
				IPath projectRelativePath = gitPath
						.makeRelativeTo(projects[0].getLocation());
				if (projectRelativePath.isEmpty()) {
					return projects[0];
				} else {
					return projects[0].getFile(projectRelativePath);
				}
