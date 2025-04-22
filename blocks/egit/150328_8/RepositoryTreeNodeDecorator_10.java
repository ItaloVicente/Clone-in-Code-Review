	private void decorateRepositoryGroup(RepositoryTreeNode<?> node,
			IDecoration decoration) {
		RepositoryCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache();
		RepositoryGroup group = ((RepositoryGroupNode) node).getGroup();
		boolean markGroupDirty = false;
		for (File repoDir : group.getRepositoryDirectories()) {
			Repository repo = cache.getRepository(repoDir);
			if (repo != null && RepositoryUtil.hasChanges(repo)) {
				markGroupDirty = true;
				break;
			}
		}
		if (markGroupDirty) {
			decoration.addPrefix(HAS_CHANGES_PREFIX);
		} else {
			ensureCorrectLabelCaching(decoration);
		}
	}

	private void ensureCorrectLabelCaching(IDecoration decoration) {
		decoration.addSuffix(" ");//$NON-NLS-1$
	}

