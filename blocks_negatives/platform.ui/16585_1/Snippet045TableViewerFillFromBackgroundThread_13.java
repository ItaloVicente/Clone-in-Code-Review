		/*
		 * (non-Javadoc)
		 *
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
