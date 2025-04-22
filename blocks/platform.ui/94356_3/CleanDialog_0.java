		filteredTree = new FilteredTree(area, SWT.NONE, new PatternFilter(), true) {
			@Override
			protected TreeViewer doCreateTreeViewer(Composite parentComposite, int style) {
				return createProjectSelectionTreeViewer(parentComposite, style);
			}
		};

