		}
		IResourceVariant variant = Adapters.adapt(revision,
				IResourceVariant.class);
		if (variant instanceof GitRemoteResource) {
			final RevCommit commit = ((GitRemoteResource) variant)
					.getCommitId();
			try {
				return walk.parseCommit(commit);
			} catch (IOException e) {
				Activator
						.logError(
								NLS.bind(CoreText.GitFileHistory_invalidCommit,
										commit.getName(), resource.getName()),
								e);
