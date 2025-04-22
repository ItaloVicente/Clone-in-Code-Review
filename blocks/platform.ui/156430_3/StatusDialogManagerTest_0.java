		if (!Platform.OS_WIN32.equals(Platform.getOS())) { // Disabling on Windows due to bug 559353
			wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
			selectWidget(StatusDialogUtil.getDetailsButton());
			int sizeY = StatusDialogUtil.getStatusShell().getSize().y;
			selectWidget(StatusDialogUtil.getOkButton());
			MultiStatus ms = new MultiStatus("org.eclipse.ui.tests", 0, MESSAGE_1, null);
			for (int i = 0; i < 50; i++) {
				ms.add(new Status(IStatus.ERROR, "org.eclipse.ui.tests", MESSAGE_2));
			}
			wsdm.addStatusAdapter(new StatusAdapter(ms), false);
			selectWidget(StatusDialogUtil.getDetailsButton());
			Shell shell = StatusDialogUtil.getStatusShell();
			Rectangle newSize = shell.getBounds();
			assertTrue(newSize.height > sizeY);
