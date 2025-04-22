	public void refreshIgnoreNode(IResource resource) throws IOException {
		if (db.getIgnoreCache() != null) {
			db.getIgnoreCache().addIgnoreNode(resource.getParent().getLocation().toFile());
		}
	}

	public void refreshBase() throws IOException {
		if (db.getIgnoreCache() != null) {
			db.getIgnoreCache().readRulesAtBase();
		}
	}

	public static boolean isIgnored(IResource rsrc) throws IOException {
		RepositoryMapping m = getMapping(rsrc);
		if (m != null) {
			return m.getRepository().getIgnoreCache().isIgnored(m.getRepoRelativePath(rsrc));
		}
		return false;
	}

