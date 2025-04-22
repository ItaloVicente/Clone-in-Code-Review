
	public void testBug520372AutoActivationDelayTab() throws Exception {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setAutoActivationDelay(1000);
		window.setAutoActivationCharacters(null);
		window.setKeyStroke(null);
		window.open();

		sendKeyDownToControl(SWT.TAB);
		ensurePopupIsUp();

		assertOneShellUp();
	}

	public void testBug520372AutoActivationDelayCR() throws Exception {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setAutoActivationDelay(1000);
		window.setAutoActivationCharacters(null);
		window.setKeyStroke(null);
		window.open();

		sendKeyDownToControl(SWT.CR);
		ensurePopupIsUp();

		assertOneShellUp();
	}

	public void testBug520372AutoActivationDelayESC() throws Exception {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setAutoActivationDelay(1000);
		window.setAutoActivationCharacters(null);
		window.setKeyStroke(null);
		window.open();

		sendKeyDownToControl(SWT.ESC);
		ensurePopupIsUp();

		assertOneShellUp();
	}
