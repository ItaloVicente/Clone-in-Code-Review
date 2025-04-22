	private class TableSchemeSelectionListener implements ICheckStateListener, ISelectionChangedListener {
		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.
		 * eclipse.jface.viewers.SelectionChangedEvent)
		 */
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			handleSelection();

