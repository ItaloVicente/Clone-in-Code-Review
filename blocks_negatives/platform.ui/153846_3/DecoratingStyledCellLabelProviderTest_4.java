		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return entries;
			}

			@Override
			public void inputChanged(org.eclipse.jface.viewers.Viewer viewer, Object oldInput, Object newInput) {
			}

		});
