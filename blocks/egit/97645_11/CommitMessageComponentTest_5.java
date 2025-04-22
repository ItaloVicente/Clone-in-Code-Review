	public void testCaretPosition() {
		try {
			StagingViewTester stagingView = StagingViewTester.openStagingView();
			assertEquals(
					CommitMessageProviderFactory.getProvidedCaretPosition(),
					stagingView.getCaretPosition());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
