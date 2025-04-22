		FilteredTree tree = new FilteredTree(main, SWT.SINGLE | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL, new PatternFilter(), true);

		tv = tree.getViewer();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);
		tv.setContentProvider(new RepositoriesViewContentProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				return false;
			}
		});
