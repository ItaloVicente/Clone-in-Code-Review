		IStructuredSelection selection = getSelection();
		return getData(selection) != null;
	}

	private static Data getData(IStructuredSelection selection) {
		if (selection.size() != 1)
			return null;

		Object element = selection.getFirstElement();
		if (element instanceof IResource) {
			IResource resource = (IResource) element;

			if (resource instanceof IStorage) {
				IStorage storage = (IStorage) resource;
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);

				if (mapping != null) {
					String repoRelativePath = mapping
							.getRepoRelativePath(resource);
					return new Data(mapping.getRepository(), repoRelativePath,
							storage, null);
				}
			}
		} else if (element instanceof CommitFileRevision) {
			CommitFileRevision revision = (CommitFileRevision) element;
			try {
				IStorage storage = revision.getStorage(new NullProgressMonitor());
				return new Data(revision.getRepository(),
						revision.getGitPath(), storage, revision.getRevCommit());
			} catch (CoreException e) {
				return null;
			}
		}
		return null;
	}

	private static class Data {
		private final Repository repository;
		private final String repositoryRelativePath;
		private final IStorage storage;
		private final RevCommit startCommit;

		public Data(Repository repository, String repositoryRelativePath,
				IStorage storage, RevCommit startCommit) {
			this.repository = repository;
			this.repositoryRelativePath = repositoryRelativePath;
			this.storage = storage;
			this.startCommit = startCommit;
		}
