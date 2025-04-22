	private static void testKeyEvent(Event keyEvent, String firstMatch,
			String secondMatch, String thirdMatch) throws ParseException {
		KeyStroke desiredKeyStroke = null;
		KeyStroke actualKeyStroke = null;

		desiredKeyStroke = KeyStroke.getInstance(firstMatch); //$NON-NLS-1$
		actualKeyStroke = SWTKeySupport
				.convertAcceleratorToKeyStroke(SWTKeySupport
						.convertEventToUnmodifiedAccelerator(keyEvent));
		assertEquals(
				"Unmodified character with all modifiers doesn't match.", desiredKeyStroke, actualKeyStroke); //$NON-NLS-1$

		desiredKeyStroke = KeyStroke.getInstance(secondMatch); //$NON-NLS-1$
		actualKeyStroke = SWTKeySupport
				.convertAcceleratorToKeyStroke(SWTKeySupport
						.convertEventToUnshiftedModifiedAccelerator(keyEvent));
		assertEquals(
				"Modified character with no shift doesn't match.", desiredKeyStroke, actualKeyStroke); //$NON-NLS-1$

		desiredKeyStroke = KeyStroke.getInstance(thirdMatch); //$NON-NLS-1$
		actualKeyStroke = SWTKeySupport
				.convertAcceleratorToKeyStroke(SWTKeySupport
						.convertEventToModifiedAccelerator(keyEvent));
		assertEquals(
				"Modified character with all modifiers doesn't match.", desiredKeyStroke, actualKeyStroke); //$NON-NLS-1$
	}

	public Bug42035Test(String testName) {
		super(testName);
	}

	public void testCtrl() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = 0x40000;
		keyEvent.character = 0x00;
		keyEvent.stateMask = SWT.NONE;

		KeyStroke desiredKeyStroke = KeyStroke.getInstance("CTRL+"); //$NON-NLS-1$
		KeyStroke actualKeyStroke = SWTKeySupport
				.convertAcceleratorToKeyStroke(SWTKeySupport
						.convertEventToUnmodifiedAccelerator(keyEvent));
		assertEquals(
				"Unmodified character with all modifiers doesn't match", desiredKeyStroke, actualKeyStroke); //$NON-NLS-1$
	}

	public void testCtrlEnter() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = 0x0D;
		keyEvent.character = 0x0D;
		keyEvent.stateMask = SWT.CTRL;

		testKeyEvent(keyEvent, "CTRL+CR", "CTRL+CR", "CTRL+CR"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public void testCtrlM() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = 0x6D;
		keyEvent.character = 0x0D;
		keyEvent.stateMask = SWT.CTRL;

		testKeyEvent(keyEvent, "CTRL+M", "CTRL+M", "CTRL+M"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public void testCtrlShift2() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = '2';
		keyEvent.character = 0x00;
		keyEvent.stateMask = SWT.CTRL | SWT.SHIFT;

		testKeyEvent(keyEvent, "CTRL+SHIFT+2", "CTRL+@", "CTRL+SHIFT+@"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public void testCtrlShift7_SwissGerman() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = '7';
		keyEvent.character = '/';
		keyEvent.stateMask = SWT.CTRL | SWT.SHIFT;

		testKeyEvent(keyEvent, "CTRL+SHIFT+7", "CTRL+/", "CTRL+SHIFT+/"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public void testCtrlShift7_US() throws ParseException {
		Event keyEvent = new Event();
		keyEvent.keyCode = '7';
		keyEvent.character = '&';
		keyEvent.stateMask = SWT.CTRL | SWT.SHIFT;

		testKeyEvent(keyEvent, "CTRL+SHIFT+7", "CTRL+&", "CTRL+SHIFT+&"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
