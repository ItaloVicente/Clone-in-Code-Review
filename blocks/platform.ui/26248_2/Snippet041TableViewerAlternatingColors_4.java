		column.getColumn().setText(label);
		column.setLabelProvider(createLabelProviderFor(viewer));
		return column;
	}

	private ColumnLabelProvider createLabelProviderFor(final TableViewer viewer) {
		return new ColumnLabelProvider() {
			boolean isEvenIdx = true;
