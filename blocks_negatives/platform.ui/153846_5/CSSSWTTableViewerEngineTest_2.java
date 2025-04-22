			tableViewer.setContentProvider(new IStructuredContentProvider() {
				public void dispose() {

				}

				public Object[] getElements(Object inputElement) {
					return datas.toArray();
				}

				public void inputChanged(Viewer viewer, Object oldInput,
						Object newInput) {

				}
			});
