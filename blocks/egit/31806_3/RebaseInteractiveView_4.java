		final TreeViewerColumn stepColumn = createColumn("Step", 55); //$NON-NLS-1$
		stepColumn.setLabelProvider(new HighlightingColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PlanElement) {
					PlanElement planLine = (PlanElement) element;
					return Integer.toString(planIndexer.indexOf(planLine) + 1)
							+ "."; //$NON-NLS-1$
				}
				return super.getText(element);
			}
		});
		stepColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				planTreeViewer.getTree().setSortColumn(stepColumn.getColumn());
				int direction = planTreeViewer.getTree().getSortDirection();
				int newDirection = (direction == SWT.UP ? SWT.DOWN : SWT.UP);
				planTreeViewer.getTree().setSortDirection(newDirection);

				RebaseInteractivePreferences
						.setOrderReversed(newDirection == SWT.DOWN);

				TreeItem topmostVisibleItem = planTreeViewer.getTree()
						.getTopItem();
				refreshUI();
				if (topmostVisibleItem != null)
					planTreeViewer.getTree().showItem(topmostVisibleItem);
			}
		});

		boolean direct = RebaseInteractivePreferences.isOrderReversed();
		int direction = (direct ? SWT.DOWN : SWT.UP);
		planTreeViewer.getTree().setSortColumn(stepColumn.getColumn());
		planTreeViewer.getTree().setSortDirection(direction);

