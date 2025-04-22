		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping == null) {
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		}
		final String gitPath = mapping.getRepoRelativePath(resource);
		if (gitPath == null) {
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		}
		final Repository repository = mapping.getRepository();
		String baseline = GitQuickDiffProvider.baseline.get(repository);
		if (baseline == null)
			baseline = Constants.HEAD;
		ObjectId commitId = repository.resolve(baseline);
		if (commitId != null) {
			if (commitId.equals(lastCommit)) {
				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.QUICKDIFF.getLocation(),
							"(GitDocument) already resolved"); //$NON-NLS-1$
