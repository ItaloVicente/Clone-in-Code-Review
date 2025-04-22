		tableViewer.setInput(null);

		final TableColumnLayout layout = new TableColumnLayout();
		tablePanel.setLayout(layout);

		final TableViewerColumn modeViewer = createColumn(layout,
				UIText.PushResultTable_columnMode, COLUMN_MODE_WEIGHT,
				SWT.CENTER);
		modeViewer.setLabelProvider(new CenteredImageLabelProvider() {
			@Override
			public Image getImage(Object element) {
				if (((RefUpdateElement) element).isDelete())
					return imageRegistry.get(IMAGE_DELETE);
				return imageRegistry.get(IMAGE_ADD);
			}

			@Override
			public String getToolTipText(Object element) {
				if (((RefUpdateElement) element).isDelete())
					return UIText.RefSpecPanel_modeDeleteDescription;
				return UIText.RefSpecPanel_modeUpdateDescription;
			}
		});

		final TableViewerColumn srcViewer = createColumn(layout,
				UIText.PushResultTable_columnSrc, COLUMN_SRC_WEIGHT, SWT.LEFT);
		srcViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((RefUpdateElement) element).getSrcRefName();
			}
		});

		final TableViewerColumn dstViewer = createColumn(layout,
				UIText.PushResultTable_columnDst, COLUMN_DST_WEIGHT, SWT.LEFT);
		dstViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((RefUpdateElement) element).getDstRefName();
			}
		});
