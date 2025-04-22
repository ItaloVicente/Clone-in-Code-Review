		column.setLabelProvider(new CheckboxLabelProvider(tableViewer
				.getControl()) {
			@Override
			protected boolean isChecked(final Object element) {
				return ((RefSpec) element).isForceUpdate();
			}
