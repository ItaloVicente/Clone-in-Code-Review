				topIndex = initCommitsMap(asArray, topCommitName);
			} else {
				topIndex = findCommit(asArray, topCommitName);
			}
		}
		allCommitsArray = asArray;
		allCommitsLength = newAllCommitsLength;
		table.setInput(asArray);
		if (newAllCommitsLength > 0) {
			if (commitToShow != null) {
				selectCommit(commitToShow);
			}
			if (keepPosition && topIndex >= 0) {
				t.setTopIndex(topIndex);
