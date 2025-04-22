	private void processRemainingUiEvents() {
		UITestCase.processEvents();
		int count = 0;
		while (count < 3 && (StatusDialogUtil.getStatusShell() != null)) {
			UITestCase.processEvents();
			count++;
		}
	}

	private void assertStatusShellOpen() {
		int count = 0;
		while (count < 42 && (StatusDialogUtil.getStatusShell() == null)) {
			UITestCase.processEvents();
			count++;
		}
		assertNotNull("Status shell was not shown!", StatusDialogUtil.getStatusShell());
	}

