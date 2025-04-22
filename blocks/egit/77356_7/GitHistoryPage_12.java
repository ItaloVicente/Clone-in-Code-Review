					final Object currentInput = getInput();
					searchBar.setInput(new ICommitsProvider() {

						@Override
						public Object getSearchContext() {
							return currentInput;
						}

						@Override
						public SWTCommit[] getCommits() {
							return asArray;
						}

						@Override
						public RevFlag getHighlight() {
							return highlightFlag;
						}
					});
					actions.findAction.setEnabled(true);
					if (store.getBoolean(
							UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR)) {
						searchBar.setVisible(true);
					}
