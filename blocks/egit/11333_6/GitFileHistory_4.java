		return NO_REVISIONS;
	}

	private String getGitPath(IFileRevision revision) {
		if (revision instanceof CommitFileRevision)
			return ((CommitFileRevision) revision).getGitPath();
		else if (revision instanceof IAdaptable) {
			final IResourceVariant variant = (IResourceVariant) ((IAdaptable) revision)
					.getAdapter(IResourceVariant.class);

			if (variant instanceof GitRemoteResource)
				return ((GitRemoteResource) variant).getPath();
		}

		return null;
	}

	private RevCommit getRevCommit(IFileRevision revision) {
		if (revision instanceof CommitFileRevision)
			return ((CommitFileRevision) revision).getRevCommit();
		else if (revision instanceof IAdaptable) {
			final IResourceVariant variant = (IResourceVariant) ((IAdaptable) revision)
					.getAdapter(IResourceVariant.class);
			if (variant instanceof GitRemoteResource) {
				final RevCommit commit = ((GitRemoteResource) variant)
						.getCommitId();
				try {
					return walk.parseCommit(commit);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							CoreText.GitFileHistory_invalidCommit,
							commit.getName(), resource.getName()), e);
				}
			}
		}

		return null;
