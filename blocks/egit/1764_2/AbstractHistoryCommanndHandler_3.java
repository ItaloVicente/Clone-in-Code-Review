		if (input instanceof RepositoryTreeNode)
			return ((RepositoryTreeNode) input).getRepository();
		return RepositoryMapping.getMapping((IResource) input).getRepository();
	}

	protected String getRepoRelativePath(Repository repo, File file) {
		IPath workdirPath = new Path(repo.getWorkTree().getPath());
		IPath filePath = new Path(file.getPath()).setDevice(null);
		return filePath.removeFirstSegments(workdirPath.segmentCount())
				.toString();
