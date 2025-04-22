		viewer.setContentProvider(new IStructuredContentProvider() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
			 */
			public void dispose() {
			};

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
			 */
			public Object[] getElements(Object inputElement) {
				return entries;
			};

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
			 *      java.lang.Object, java.lang.Object)
			 */
			public void inputChanged(org.eclipse.jface.viewers.Viewer viewer,
					Object oldInput, Object newInput) {
			}

		});
