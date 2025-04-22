		serviceLocator = (ServiceLocator) slc.createServiceLocator(null, null, new IDisposable() {
			@Override
			public void dispose() {
				final Display display = getDisplay();
				if (display != null && !display.isDisposed()) {
					MessageDialog.openInformation(null,
							WorkbenchMessages.Workbench_NeedsClose_Title,
							WorkbenchMessages.Workbench_NeedsClose_Message);
					close(PlatformUI.RETURN_RESTART, true);
				}
