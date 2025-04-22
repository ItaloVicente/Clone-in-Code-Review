		PatternFilter filter = new PatternFilter() {

			@Override
			public boolean isElementVisible(Viewer viewer, Object element) {

				if (getCheckedItems().contains(element))
					return true;

				return super.isElementVisible(viewer, element);
			}
		};

		fTree = new FilteredCheckboxTree(searchResultGroup, null, SWT.NONE,
				filter);
		fTreeViewer = fTree.getCheckboxTreeViewer();
		fTreeViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				enableOk();
			}
		});
