		final TableViewerColumn srcViewer = createColumn(layout,
				UIText.FetchResultTable_columnSrc, COLUMN_SRC_WEIGHT, SWT.LEFT);
		srcViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TrackingRefUpdate) element).getRemoteName();
