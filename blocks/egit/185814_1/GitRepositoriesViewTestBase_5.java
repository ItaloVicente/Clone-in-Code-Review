	protected void assertWizardDialogMessage(SWTBot dialogBot,
			String expectedText) {
		Version jFaceVersion = Platform.getBundle("org.eclipse.jface")
				.getVersion();
		if (jFaceVersion.compareTo(Version.valueOf("3.22.0")) < 0
				|| jFaceVersion.compareTo(Version.valueOf("3.23.0")) >= 0) {
			dialogBot.text(expectedText);
		} else {
			boolean result = UIThreadRunnable
					.<Boolean> syncExec(() -> Boolean.valueOf(dialogBot
							.widgets(widgetOfType(Label.class)).stream()
							.anyMatch(l -> l.getText().equals(expectedText))))
					.booleanValue();
			assertTrue("No label with text '" + expectedText + "' found",
					result);
		}
	}

