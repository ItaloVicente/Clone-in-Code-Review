
	private class JobListener extends JobChangeAdapter {
		@Override
		public void done(IJobChangeEvent event) {
			Display.getDefault().asyncExec(() -> {
				if (!UriSchemeHandlerPreferencePage.this.tableViewer.getControl().isDisposed()) {
					UriSchemeHandlerPreferencePage.this.setDataOnTableViewer();
					UriSchemeHandlerPreferencePage.this.tableViewer.getControl().setEnabled(true);
					UriSchemeHandlerPreferencePage.this.loadingTextComposite.setVisible(false);
				}
			});
		}
	}
