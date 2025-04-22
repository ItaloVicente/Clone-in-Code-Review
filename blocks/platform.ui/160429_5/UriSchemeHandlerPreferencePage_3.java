
	private final class OsRegistrationReadingJob extends Job {
		private OsRegistrationReadingJob() {
			super("Retrieving Link Handlers registration status from Operating System"); //$NON-NLS-1$
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			Collection<UiSchemeInformation> schemeInformationList = retrieveSchemeInformationList();
			if (!schemeInformationList.isEmpty()) {
				Display.getDefault().asyncExec(() -> {
					if (!tableViewer.getControl().isDisposed()) {
						setDataOnTableViewer(schemeInformationList);
						UriSchemeHandlerPreferencePage.this.tableViewer.getControl().setEnabled(true);
					}
				});
			}
			return Status.OK_STATUS;
		}
	}

