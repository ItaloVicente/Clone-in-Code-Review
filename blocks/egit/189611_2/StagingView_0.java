
		unstagedViewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				StagingEntry entry = getStagingEntry(element);
				if (entry != null && !entry.isTracked()) {
					return showUntrackedAction.isChecked();
				}
				return true;
			}
		});

