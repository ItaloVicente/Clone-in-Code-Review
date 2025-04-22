	}

	public void assertMergeSelected() {
		assertConfigureUpstreamSelected();
		assertTrue(wizard.radio(UIText.UpstreamConfigComponent_MergeRadio)
				.isSelected());
	}

	public void assertRebaseSelected() {
		assertConfigureUpstreamSelected();
