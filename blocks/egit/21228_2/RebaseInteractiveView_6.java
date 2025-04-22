
		TreeViewerColumn authorColumn = createColumn(headings[4], 120);
		authorColumn.setLabelProvider(new HighlightingColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PlanElement) {
					PlanElement planLine = (PlanElement) element;
					return planLine.getAuthor();
				}
				return super.getText(element);
			}
		});

		TreeViewerColumn authoredDateColumn = createColumn(headings[5], 80);
		authoredDateColumn
				.setLabelProvider(new HighlightingColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof PlanElement) {
							PlanElement planLine = (PlanElement) element;
							return planLine.getAuthoredDate(dateFormatter);
						}
						return super.getText(element);
					}
				});

		TreeViewerColumn committerColumn = createColumn(headings[6], 120);
		committerColumn.setLabelProvider(new HighlightingColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PlanElement) {
					PlanElement planLine = (PlanElement) element;
					return planLine.getCommitter();
				}
				return super.getText(element);
			}
		});

		TreeViewerColumn commitDateColumn = createColumn(headings[7], 80);
		commitDateColumn
				.setLabelProvider(new HighlightingColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof PlanElement) {
							PlanElement planLine = (PlanElement) element;
							return planLine.getCommittedDate(dateFormatter);
						}
						return super.getText(element);
					}
				});
		dynamicColumns = new TreeViewerColumn[] { commitMessageColumn,
				authorColumn, authoredDateColumn, committerColumn,
				commitDateColumn };
	}

	private TreeViewerColumn createColumn(String text, int width) {
		TreeViewerColumn column = new TreeViewerColumn(planTreeViewer, SWT.NONE);
		column.getColumn().setText(text);
		column.getColumn().setMoveable(false);
		column.getColumn().setResizable(true);
		column.getColumn().setWidth(width);
		return column;
