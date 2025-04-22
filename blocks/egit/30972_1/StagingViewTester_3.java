		jobJoiner.join();
	}

	public void assertCommitEnabled(boolean expectEnabled) {
		boolean actual = stagingView.bot().button(UIText.StagingView_Commit)
				.isEnabled();
		if (expectEnabled)
			assertTrue("Expected Commit button to be enabled", actual);
		else
			assertFalse("Expected Commit button to be disabled", actual);
