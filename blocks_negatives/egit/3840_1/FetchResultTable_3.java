		final TableViewerColumn dstViewer = createColumn(layout,
				UIText.FetchResultTable_columnDst, COLUMN_DST_WEIGHT, SWT.LEFT);
		dstViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TrackingRefUpdate) element).getLocalName();
