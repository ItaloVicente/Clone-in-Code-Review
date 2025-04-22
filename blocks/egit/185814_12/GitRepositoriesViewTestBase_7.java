	protected void assertWizardDialogMessage(SWTBot dialogBot,
			String expectedText) {
		Version jFaceVersion = Platform.getBundle("org.eclipse.jface")
				.getVersion();
		if (jFaceVersion.compareTo(Version.valueOf("3.22.0")) < 0
				|| jFaceVersion.compareTo(Version.valueOf("3.23.0")) >= 0) {
			dialogBot.text(expectedText);
		} else {
			dialogBot.label(expectedText);
		}
	}

