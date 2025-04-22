	public void assertRebaseSelected() {
		assertTrue(wizard.checkBox(
				UIText.UpstreamConfigComponent_ConfigureUpstreamCheck)
				.isChecked());
		assertTrue(wizard.radio(UIText.UpstreamConfigComponent_RebaseRadio)
				.isSelected());
	}

	public boolean isUpstreamConfigOverwriteWarningShown() {
		return wizard.text(1).getText()
				.contains(UIText.PushBranchPage_UpstreamConfigOverwriteWarning);
	}

