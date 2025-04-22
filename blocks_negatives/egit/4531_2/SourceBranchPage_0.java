		PatternFilter filter = new PatternFilter() {
			@Override
			public boolean isElementVisible(Viewer viewer, Object element) {
				if (getSelectedBranches().contains(element))
					return true;
				return super.isElementVisible(viewer, element);
			}
		};

		FilteredCheckboxTree fTree = new FilteredCheckboxTree(panel, null, SWT.NONE,
				filter);
