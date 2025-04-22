	@Override
	protected ColumnLabelProvider getColumnLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof EditorReference) {
					EditorReference er = ((EditorReference) element);
					if (er.isDirty()) {
					}
					return er.getTitle();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof EditorReference) {
					return ((EditorReference) element).getTitleImage();
				}
				return super.getImage(element);
			}

			@Override
			public String getToolTipText(Object element) {
				if (element instanceof EditorReference) {
					return ((EditorReference) element).getTitleToolTip();
				}
				return super.getToolTipText(element);
			};
		};
	}

