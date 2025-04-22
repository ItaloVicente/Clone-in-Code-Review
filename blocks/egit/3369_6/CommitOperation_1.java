		if (filesToCommit != null)
			commitFileList = new HashSet<String>(filesToCommit);
		if (notIndexed != null)
			this.notIndexed = new HashSet<String>(notIndexed);
		if (notTracked != null)
			this.notTracked = new HashSet<String>(notTracked);
	}

	private void setRepository(IFile file) throws CoreException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		if (mapping == null)
			throw new CoreException(Activator.error(NLS.bind(
					CoreText.CommitOperation_couldNotFindRepositoryMapping,
					file), null));
		repo = mapping.getRepository();
	}

	public void setRepository(Repository repository) {
		repo = repository;
	}

	private Collection<String> buildFileList(Collection<IFile> files) throws CoreException {
		Collection<String> result = new HashSet<String>();
		for (IFile file : files) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(file);
			if (mapping == null)
				throw new CoreException(Activator.error(NLS.bind(CoreText.CommitOperation_couldNotFindRepositoryMapping, file), null));
			String repoRelativePath = mapping.getRepoRelativePath(file);
			result.add(repoRelativePath);
		}
		return result;
