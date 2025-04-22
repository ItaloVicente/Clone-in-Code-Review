			public void widgetSelected(SelectionEvent e) {
				final ILabelProvider labelProvider = new LabelProvider() {
					public String getText(Object element) {
						return ((Map.Entry) element).getKey()
					}
				};
