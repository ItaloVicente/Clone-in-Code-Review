		for (IFile file : selectedItems) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(file);
			if (mapping == null)
				throw new CoreException(Activator.error(NLS.bind(CoreText.CommitOperation_couldNotFindRepositoryMapping, file), null));
			String repoRelativePath = mapping.getRepoRelativePath(file);
			Repository repository = mapping.getRepository();
