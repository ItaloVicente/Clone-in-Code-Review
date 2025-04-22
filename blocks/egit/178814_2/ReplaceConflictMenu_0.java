		RevCommit ours = SelectionRepositoryStateCache.INSTANCE
				.getHeadCommit(repo);
		if (ours != null) {
			items.add(createOursItem(ReplaceConflictActionHandler.formatCommitLabel(
					UIText.ReplaceWithOursTheirsMenu_OursWithCommitLabel, ours),
					repo, entries));
