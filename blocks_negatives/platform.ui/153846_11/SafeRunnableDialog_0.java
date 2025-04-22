	/**
	 * Return the content provider for the statuses.
	 *
	 * @return IStructuredContentProvider
	 */
	private IStructuredContentProvider getStatusContentProvider() {
		return new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return statuses.toArray();
			}

			@Override
			public void dispose() {

			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {

			}
		};
	}

