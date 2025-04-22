			GitTraceLocation.getTrace().trace(
					GitTraceLocation.QUICKDIFF.getLocation(),(GitDocument) populate:  + resource); //$NON-NLS-1$
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping == null) {
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		}
		final String gitPath = mapping.getRepoRelativePath(resource);
		final Repository repository = mapping.getRepository();
		String baseline = GitQuickDiffProvider.baseline.get(repository);
		if (baseline == null)
			baseline = Constants.HEAD;
		ObjectId commitId = repository.resolve(baseline);
		if (commitId != null) {
			if (commitId.equals(lastCommit)) {
