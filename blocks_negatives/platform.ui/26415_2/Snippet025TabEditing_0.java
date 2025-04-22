	private class MyContentProvider implements IStructuredContentProvider {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return (MyModel[])inputElement;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
			
		}
