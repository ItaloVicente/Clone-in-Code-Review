		filteredTree = new FilteredTree(area, SWT.NONE, new PatternFilter(), true) {
			@Override
			protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
				return createProjectSelectionTreeViewer(parent, style);
			}

		};

