		else if (revision instanceof IAdaptable) {
			IResourceVariant variant = Adapters.adapt(((IAdaptable) revision),
					IResourceVariant.class);
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
