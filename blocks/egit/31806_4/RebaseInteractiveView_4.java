		TreeViewerColumn stepColumn = createColumn(headings[1], 55);
		stepColumn.setLabelProvider(new HighlightingColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PlanElement) {
					PlanElement planLine = (PlanElement) element;
					return (planIndexer.indexOf(planLine) + 1) + "."; //$NON-NLS-1$
				}
				return super.getText(element);
			}
		});
		stepColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Tree tree = planTreeViewer.getTree();

				boolean orderReversed = tree.getSortDirection() == SWT.DOWN;

				RebaseInteractivePreferences.setOrderReversed(!orderReversed);

				int newDirection = (orderReversed ? SWT.UP : SWT.DOWN);
				tree.setSortDirection(newDirection);

				TreeItem topmostVisibleItem = tree.getTopItem();
				refreshUI();
				if (topmostVisibleItem != null)
					tree.showItem(topmostVisibleItem);
			}
		});

		boolean orderReversed = RebaseInteractivePreferences.isOrderReversed();
		int direction = (orderReversed ? SWT.DOWN : SWT.UP);

		Tree planTree = planTreeViewer.getTree();
		planTree.setSortColumn(stepColumn.getColumn());
		planTree.setSortDirection(direction);

		TreeViewerColumn actionColumn = createColumn(headings[2], 90);
