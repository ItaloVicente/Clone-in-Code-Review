	private IResourceVariant getMember(IContainer container,
			Repository repository, String entryName) throws TeamException {
		String gitPath = RepositoryMapping.getMapping(container)
				.getRepoRelativePath(container);
		Tree merge = trees.get(repository);
		try {
			TreeEntry tree = merge.findBlobMember(entryName);
			GitBlobResourceVariant blobVariant = new GitBlobResourceVariant(
					container.getFile(new Path(entryName)), repository, tree
							.getId(), dates.get(entryName));
			return blobVariant;
		} catch (IOException e) {
			throw new TeamException(new Status(IStatus.ERROR, Activator
					.getPluginId(), NLS.bind(
					CoreText.GitResourceVariantTree_couldNotFindBlob, gitPath),
					e));
		}
