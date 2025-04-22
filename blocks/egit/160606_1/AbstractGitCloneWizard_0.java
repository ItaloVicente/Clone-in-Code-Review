			public void setPageComplete(boolean complete) {
				if (complete) {
					RepositorySelection selection = getRepositorySelection();
					if (selection != null) {
						cloneDestination.setSelection(selection,
								validSource.getAvailableBranches(),
								validSource.getSelectedBranches(),
								validSource.getHEAD());
					}
				} else {
					cloneDestination.setPageComplete(false);
