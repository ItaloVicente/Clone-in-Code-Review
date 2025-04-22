	protected void handleDispose(DisposeEvent event) {
		if (contentProvider != null) {
			try {
				contentProvider.inputChanged(this, getInput(), null);
			} catch (Exception e) {
				String message = "Exception while calling ContentProvider.inputChanged from ContentViewer.handleDispose"; //$NON-NLS-1$
				message += " (" + contentProvider.getClass().getName() + ")"; //$NON-NLS-1$//$NON-NLS-2$
				Policy.getLog().log(new Status(IStatus.WARNING, Policy.JFACE, message, e));
			}
			contentProvider.dispose();
			contentProvider = null;
		}
		if (labelProvider != null) {
			labelProvider.removeListener(labelProviderListener);
			labelProvider.dispose();
			labelProvider = null;
		}
		input = null;
	}
