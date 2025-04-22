		TreeWalk tw = null;
		RevWalk rw = null;
		try {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping == null) {
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			}
			final String gitPath = mapping.getRepoRelativePath(resource);
			if (gitPath == null) {
				setResolved(null, null, null, ""); //$NON-NLS-1$
