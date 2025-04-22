
				if (event.getChecked()) {
					setPageComplete(selectedBranches.size() == selectedRepositories
							.size());
				} else if (treeViewer.getCheckedElements().length == 0) {
					setPageComplete(false);
				}
