	private void discardChange(IResource res, Repository repository)
			throws IOException {
		String resRelPath = RepositoryMapping.getMapping(res)
				.getRepoRelativePath(res);
		DirCache dc = repository.lockDirCache();
		try {
			DirCacheEntry entry = dc.getEntry(resRelPath);
			if (entry != null) {
				File file = new File(res.getLocationURI());
				DirCacheCheckout.checkoutEntry(repository, file, entry);
			}
		} finally {
			dc.unlock();
		}
	}
