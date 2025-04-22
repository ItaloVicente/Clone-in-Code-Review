		Version jFaceVersion = Platform.getBundle("org.eclipse.jface")
				.getVersion();
		if (jFaceVersion.compareTo(Version.valueOf("3.22.0")) < 0
				|| jFaceVersion.compareTo(Version.valueOf("3.23.0")) >= 0) {
			return wizard.text(1).getText().contains(
					UIText.PushBranchPage_UpstreamConfigOverwriteWarning);
		} else {
			return UIThreadRunnable.<Boolean> syncExec(() -> Boolean.valueOf(
					getLabels().stream().anyMatch(l -> l.getText().contains(
							UIText.PushBranchPage_UpstreamConfigOverwriteWarning))))
					.booleanValue();
		}
	}

	private List<? extends Label> getLabels() {
		return wizard.widgets(widgetOfType(Label.class));
