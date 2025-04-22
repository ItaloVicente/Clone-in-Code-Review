	private void decorateRepositoryGroup(RepositoryTreeNode<?> node,
			IDecoration decoration) {
		RepositoryCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache();
		RepositoryGroup group = ((RepositoryGroupNode) node).getGroup();
		boolean markGroupDirty = false;
		Set<String> branches = new HashSet<>();
		for (File repoDir : group.getRepositoryDirectories()) {
			Repository repo = cache.getRepository(repoDir);
			if (repo != null) {
				if (RepositoryUtil.hasChanges(repo)) {
					markGroupDirty = true;
				}
				branches.add(Repository
						.shortenRefName(DecoratorRepositoryStateCache.INSTANCE
								.getFullBranchName(repo)));
				if (markGroupDirty && branches.size() > 1) {
					break;
				}
			}
		}
		boolean decorate = false;
		if (markGroupDirty) {
			decorate = true;
			decoration.addPrefix(HAS_CHANGES_PREFIX);
		}
		if (branches.size() == 1) {
			decorate = true;
			StringBuilder suffix = new StringBuilder();
			suffix.append(OPEN_BRACKET).append(branches.iterator().next())
					.append(']');
			decoration.addSuffix(suffix.toString());
		}
		if (!decorate) {
			ensureCorrectLabelCaching(decoration);
		}
	}

	private void ensureCorrectLabelCaching(IDecoration decoration) {
		decoration.addSuffix(" ");//$NON-NLS-1$
	}

