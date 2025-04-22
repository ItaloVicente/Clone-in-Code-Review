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
			throw new CoreException(Activator.error(NLS.bind(CoreText.CommitOperation_couldNotFindRepositoryMapping, file), null));
		repo = mapping.getRepository();
	}

	public void setRepository(Repository repository) {
		repo = repository;
	}

	private Collection<String> buildFileList(Collection<IFile> filesIn) throws CoreException {
		Collection<String> filesOut = new ArrayList<String>();
		for (IFile file : filesIn) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(file);
			if (mapping == null)
				throw new CoreException(Activator.error(NLS.bind(CoreText.CommitOperation_couldNotFindRepositoryMapping, file), null));
			String repoRelativePath = mapping.getRepoRelativePath(file);
			filesOut.add(repoRelativePath);
		}
		return filesOut;
