		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(final Object inputElement) {
				return ((List) inputElement).toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
