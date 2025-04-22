		serviceLocator = (ServiceLocator) slc.createServiceLocator(null, null, () -> {
			final Display display1 = getDisplay();
			if (display1 != null && !display1.isDisposed()) {
				MessageDialog.openInformation(null,
						WorkbenchMessages.Workbench_NeedsClose_Title,
						WorkbenchMessages.Workbench_NeedsClose_Message);
				close(PlatformUI.RETURN_RESTART, true);
