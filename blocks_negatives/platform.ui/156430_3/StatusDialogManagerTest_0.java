		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		selectWidget(StatusDialogUtil.getDetailsButton());
		int sizeY = StatusDialogUtil.getStatusShell().getSize().y;
		selectWidget(StatusDialogUtil.getOkButton());
		MultiStatus ms = new MultiStatus("org.eclipse.ui.tests", 0, MESSAGE_1,
				null);
		for (int i = 0; i < 50; i++) {
			ms
					.add(new Status(IStatus.ERROR, "org.eclipse.ui.tests",
							MESSAGE_2));
