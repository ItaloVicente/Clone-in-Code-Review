		String path = null;
		RevCommit commit = null;
		if (ifr instanceof CommitFileRevision) {
			final CommitFileRevision rev = (CommitFileRevision) ifr;
			path = rev.getGitPath();
			commit = rev.getRevCommit();
		} else if (ifr instanceof IAdaptable) {
			final IResourceVariant variant = (IResourceVariant) ((IAdaptable) ifr)
					.getAdapter(IResourceVariant.class);
			if (variant instanceof GitRemoteResource) {
				final GitRemoteResource gitVariant = (GitRemoteResource) variant;
				path = gitVariant.getPath();
				try {
					commit = walk.parseCommit(gitVariant.getCommitId());
				} catch (IOException e) {
				}
			}
		}
