	public void testOpenAndClose() throws Throwable {
		MockPart part = openPart(fPage);
		assertTrue(part.isSiteInitialized());
		CallHistory history = part.getCallHistory();
		assertTrue(history.verifyOrder(new String[] { "setInitializationData", "init",
				"createPartControl", "setFocus" }));
