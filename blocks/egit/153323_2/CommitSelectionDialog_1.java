		searchBar.setInput(new ICommitsProvider() {

			@Override
			public Object getSearchContext() {
				return null;
			}

			@Override
			public SWTCommit[] getCommits() {
				return allCommits.toArray(new SWTCommit[0]);
			}

			@Override
			public RevFlag getHighlight() {
				return highlightFlag;
			}
		});
