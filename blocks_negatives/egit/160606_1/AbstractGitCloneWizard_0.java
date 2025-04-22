			public void setVisible(boolean visible) {
				RepositorySelection selection = getRepositorySelection();
				if (selection != null && visible) {
					setSelection(selection,
							validSource.getAvailableBranches(),
							validSource.getSelectedBranches(),
							validSource.getHEAD());
