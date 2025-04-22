	protected ColumnLabelProvider getColumnLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof WorkbenchPartReference) {
					WorkbenchPartReference ref = ((WorkbenchPartReference) element);
					if (ref.isDirty()) {
						return "*" + ref.getTitle(); //$NON-NLS-1$
					}
					return ref.getTitle();
				}
				return super.getText(element);
			}
