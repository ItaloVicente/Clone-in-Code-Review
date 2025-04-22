		conflictViewer.setContentProvider(new IStructuredContentProvider() {

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof Collection) {
					return ((Collection) inputElement).toArray();
				}
				return new Object[0];
