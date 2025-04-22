		filteredTree = new FilteredTree(area, SWT.NONE, getPatternFilter(), true) {
			@Override
			protected TreeViewer doCreateTreeViewer(Composite parentComposite, int style) {
				return createProjectSelectionTreeViewer(parentComposite, style);
			}
		};

