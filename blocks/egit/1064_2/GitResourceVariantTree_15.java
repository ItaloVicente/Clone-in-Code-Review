	private IResourceVariant getBlobResourceVariant(IResource resource,
			GitSynchronizeData gsd) throws TeamException {
		FileTreeEntry fileEntry = getBlob(resource, gsd);
		if (fileEntry == null)
			return null;

		byte[] content;
		GitFile file = new GitFile(resource.getParent(), fileEntry);
