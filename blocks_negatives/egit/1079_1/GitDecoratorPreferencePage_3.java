				final IStructuredContentProvider contentsProvider = new IStructuredContentProvider() {
					public Object[] getElements(Object inputElement) {
						return ((Collection) inputElement).toArray();
					}

					public void dispose() {
					}

					public void inputChanged(Viewer viewer, Object oldInput,
							Object newInput) {
					}
				};
