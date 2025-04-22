	private Map<Repository, List<String>> prepareCommit(IProgressMonitor monitor)
			throws CoreException {
		Map<Repository, List<String>> filesByRepo = new HashMap<Repository, List<String>>();
		ArrayList<IFile> filesToAddToIndex = new ArrayList<IFile>();

		for (IFile file : filesToCommit) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(file);
			if (mapping == null)
				throw new CoreException(Activator.error(NLS.bind(CoreText.CommitOperation_couldNotFindRepositoryMapping, file), null));
			String repoRelativePath = mapping.getRepoRelativePath(file);
			Repository repository = mapping.getRepository();
