		allCommitsLength = newAllCommitsLength;
		if (commitToShow != null) {
			selectCommit(commitToShow);
		}
		if (keepPosition) {
			table.getTable().setTopIndex(topIndex);
		}
