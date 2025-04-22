	protected void showNavigator() {
		try {
			EditorTestHelper.showView(_navigatorInstanceId, true);
		} catch (PartInitException e) {
			fail("Should not throw an exception");
		}
