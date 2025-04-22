	private void markGitDirTeamPrivate(IProject project) throws CoreException  {
		RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
		File gitDir = repositoryMapping.getRepository().getDirectory();
		IPath gitDirPath = new Path(gitDir.getAbsolutePath());
		IPath location = project.getLocation();
		if (location.isPrefixOf(gitDirPath)) {
			IPath gitDirRelativePath = gitDirPath.removeFirstSegments(location.segmentCount());
			IResource gitDirResource = project.findMember(gitDirRelativePath);
			gitDirResource.setTeamPrivateMember(true);
		}
	}

