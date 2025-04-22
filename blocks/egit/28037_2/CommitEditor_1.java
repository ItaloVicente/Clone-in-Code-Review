	private String getFormattedHeaderTitle(String commitName) {
		if (getCommit().isStash()) {
			int stashIndex = getStashIndex(getCommit().getRepository(),
					getCommit().getRevCommit().getId());
			String stashName = MessageFormat.format("stash@'{'{0}'}'", //$NON-NLS-1$
					Integer.valueOf(stashIndex));
			return MessageFormat.format(
					UIText.CommitEditor_TitleHeaderStashedCommit,
					stashName);
		} else {
			return MessageFormat.format(UIText.CommitEditor_TitleHeaderCommit,
					commitName);
		}
	}

	private int getStashIndex(Repository repo, ObjectId id) {
		int index = 0;
		try {
			for (RevCommit commit : Git.wrap(repo).stashList().call())
				if (commit.getId().equals(id))
					return index;
				else
					index++;
			throw new IllegalStateException(
					UIText.CommitEditor_couldNotFindStashCommit);
		} catch (Exception e) {
			String message = MessageFormat.format(
					UIText.CommitEditor_couldNotGetStashIndex, id.name());
			Activator.logError(message, e);
			index = -1;
		}
		return index;
	}

