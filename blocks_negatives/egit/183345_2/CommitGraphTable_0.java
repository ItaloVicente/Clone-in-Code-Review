			boolean positionSet = false;
			if (keepPosition && topIndex >= 0) {
				t.setTopIndex(topIndex);
				positionSet = true;
			}
			if (commitToShow != null
					&& commitsMap.containsKey(commitToShow.getId().name())) {
				selectCommit(commitToShow);
				positionSet = true;
			}
			if (!positionSet && newAllCommitsLength > 0) {
				t.setTopIndex(0);
