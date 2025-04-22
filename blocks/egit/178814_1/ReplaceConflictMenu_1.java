		if (theirs != null) {
			items.add(
					createTheirsItem(ReplaceConflictActionHandler.formatCommitLabel(
							UIText.ReplaceWithOursTheirsMenu_TheirsWithCommitLabel,
							theirs), repo, entries));
		} else {
			items.add(createTheirsItem(
					UIText.ReplaceWithOursTheirsMenu_TheirsWithoutCommitLabel,
					repo, entries));
		}
		return items.toArray(new IContributionItem[0]);
