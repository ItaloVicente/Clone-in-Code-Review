				.addCommitNavigationListener(new CommitNavigationListener() {
					@Override
					public void showCommit(final RevCommit c) {
						graph.selectCommit(c);
					}
				});
