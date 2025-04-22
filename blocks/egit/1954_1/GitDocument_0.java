			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.QUICKDIFF.getLocation(), resource);
		TreeWalk tw = null;
		RevWalk rw = null;
		try {
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
					if (GitTraceLocation.QUICKDIFF.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.QUICKDIFF.getLocation(),
								"(GitDocument) already resolved"); //$NON-NLS-1$
					return;
				}
			} else {
				String msg = NLS.bind(UIText.GitDocument_errorResolveQuickdiff,
						new Object[] { baseline, resource, repository });
				Activator.logError(msg, new Throwable());
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			}
			rw = new RevWalk(repository);
			RevCommit baselineCommit;
			try {
				baselineCommit = rw.parseCommit(commitId);
			} catch (IOException err) {
				String msg = NLS
						.bind(UIText.GitDocument_errorLoadCommit, new Object[] {
								commitId, baseline, resource, repository });
				Activator.logError(msg, err);
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			}
			RevTree treeId = baselineCommit.getTree();
			if (treeId.equals(lastTree)) {
