	/**
	 * Check whether a resource has been changed relative to the checked out
	 * version. Content is assumed changed by this routine if the resource's
	 * modification time differs from what is recorded in the index, but the
	 * real content hasn't changed. The reason is performance.
	 *
	 * @param rsrc
	 * @return true if a resource differs in the workdir or index relative to
	 *         HEAD
	 *
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public boolean isResourceChanged(IResource rsrc) throws IOException, UnsupportedEncodingException {
		Repository repository = getRepository();
		GitIndex index = repository.getIndex();
		String repoRelativePath = getRepoRelativePath(rsrc);
		Tree headTree = repository.mapTree(Constants.HEAD);
		TreeEntry blob = headTree!=null ? headTree.findBlobMember(repoRelativePath) : null;
		Entry entry = index.getEntry(repoRelativePath);
		if (rsrc instanceof IFile && entry == null && blob == null)
			return false;
		if (entry == null)
		if (blob == null)
		boolean hashesDiffer = !entry.getObjectId().equals(blob.getId());
		return hashesDiffer || entry.isModified(getWorkTree());
	}

