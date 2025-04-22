		actionSetToolbarViewer
				.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		actionSetToolbarViewer.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		actionSetToolbarViewer.setContentProvider(TreeManager
				.getTreeContentProvider());
