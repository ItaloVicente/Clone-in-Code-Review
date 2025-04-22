		TreeViewerColumn orderColumn = createColumn("Order", 55); //$NON-NLS-1$
		orderColumn.setLabelProvider(new HighlightingColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PlanElement) {
					PlanElement planLine = (PlanElement) element;
					return Integer.toString(planIndexer.indexOf(planLine) + 1);
				}
				return super.getText(element);
			}
		});

