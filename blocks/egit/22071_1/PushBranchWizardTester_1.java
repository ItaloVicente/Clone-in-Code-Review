	public void assertRebaseSelected() {
		assertTrue(wizard.checkBox(
				UIText.UpstreamConfigComponent_ConfigureUpstreamCheck)
				.isChecked());
		assertTrue(wizard.radio(UIText.UpstreamConfigComponent_RebaseRadio)
				.isSelected());
	}

