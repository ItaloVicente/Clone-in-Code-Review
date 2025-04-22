	@Override
	protected ColumnLabelProvider getColumnLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof EditorReference) {
					EditorReference er = ((EditorReference) element);
					if (er.isDirty()) {
						return "*" + er.getTitle(); //$NON-NLS-1$
					}
					return er.getTitle();
				}
				return super.getText(element);
			}
