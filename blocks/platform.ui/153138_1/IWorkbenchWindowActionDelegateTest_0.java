	@Test
	@Ignore("Bug 48799")
	public void testDispose() throws Throwable {
		testRun();

		MockActionDelegate delegate = getDelegate();
		assertNotNull(delegate);

		delegate.callHistory.clear();
		removeAction();
		assertTrue(delegate.callHistory.contains("dispose"));
	}
