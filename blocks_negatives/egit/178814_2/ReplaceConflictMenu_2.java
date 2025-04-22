			ConflictCommits conflictCommits = RevUtils.getConflictCommits(
					repository, single.getPath());
			RevCommit ourCommit = conflictCommits.getOurCommit();
			RevCommit theirCommit = conflictCommits.getTheirCommit();

			if (ourCommit != null)
				result.add(createOursItem(
						formatCommit(
								UIText.ReplaceWithOursTheirsMenu_OursWithCommitLabel,
								ourCommit),
						repository, entries));
			else
				result.add(createOursItem(
						UIText.ReplaceWithOursTheirsMenu_OursWithoutCommitLabel,
						repository, entries));

			if (theirCommit != null)
				result.add(createTheirsItem(
						formatCommit(
								UIText.ReplaceWithOursTheirsMenu_TheirsWithCommitLabel,
								theirCommit),
						repository, entries));
			else
				result.add(createTheirsItem(
						UIText.ReplaceWithOursTheirsMenu_TheirsWithoutCommitLabel,
						repository, entries));

			return result;
