
	public IgnoreCache getIgnoreCache() {
		return cache;
	}


	public void refreshIgnoreNode(IResource resource) throws IOException {
		if (cache != null) {
			cache.refreshNode(resource);
		}
	}

	public static boolean isIgnored(IResource rsrc) throws IOException {
		RepositoryMapping m = getMapping(rsrc);
		if (m != null) {
			return m.getIgnoreCache().isIgnored(m.getRepoRelativePath(rsrc));
		}
		return false;
	}

