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
		refsViewer = fTree.getCheckboxTreeViewer();

		ITreeContentProvider provider = new ITreeContentProvider() {

			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object input) {
				return ((List) input).toArray();
			}

			public boolean hasChildren(Object element) {
				return false;
			}

			public Object getParent(Object element) {
				return null;
			}

			public Object[] getChildren(Object parentElement) {
				return null;
			}
		};
		refsViewer.setContentProvider(provider);
