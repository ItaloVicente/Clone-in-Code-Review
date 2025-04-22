	private void createForceColumn(final TableColumnLayout columnLayout) {
		final TableViewerColumn column = createColumn(columnLayout,
				UIText.RefSpecPanel_columnForce, COLUMN_FORCE_WEIGHT,
				SWT.CENTER);
		column.setLabelProvider(new CheckboxLabelProvider(tableViewer
				.getControl()) {
			@Override
			protected boolean isChecked(final Object element) {
				return ((RefSpec) element).isForceUpdate();
			}
