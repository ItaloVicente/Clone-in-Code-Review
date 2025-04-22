		SWTCommit[] allCommitsArray = allCommits.toArray(new SWTCommit[0]);
		table.setInput(highlightFlag, allCommits, allCommitsArray, null, true);
		searchBar.setInput(new ICommitsProvider() {

			@Override
			public Object getSearchContext() {
				return null;
			}

			@Override
			public SWTCommit[] getCommits() {
				return allCommitsArray;
			}

			@Override
			public RevFlag getHighlight() {
				return highlightFlag;
			}
		});
