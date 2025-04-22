		projectsList.addCheckStateListener(new ICheckStateListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged
			 * (org.eclipse.jface.viewers.CheckStateChangedEvent)
			 */
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(projectsList.getCheckedElements().length > 0);
