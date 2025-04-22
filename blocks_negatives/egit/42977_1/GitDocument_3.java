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
					return;
				}
			} else {
				if (repository.getRef(Constants.HEAD) == null) {
					String msg = NLS.bind(UIText.GitDocument_errorResolveQuickdiff,
							new Object[] { baseline, resource, repository });
					Activator.logError(msg, new Throwable());
				}
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
