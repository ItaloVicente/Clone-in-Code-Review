		filteredTree = new FilteredTree(area, SWT.BORDER, getPatternFilter(), true) {
			@Override
			protected TreeViewer doCreateTreeViewer(Composite parentComposite, int style) {
				return createProjectSelectionTreeViewer(parentComposite, style);
			}
		};
