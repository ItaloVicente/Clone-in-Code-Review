		resolutionsList= new TableViewer(control, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		resolutionsList.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return resolutions.keySet().toArray();
			}

			@Override
			public void dispose() {

			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {

			}
		});
