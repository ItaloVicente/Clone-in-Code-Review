		String[] testNames = new String[] { "init", "selectionChanged", "run" };
		assertEquals(Arrays.toString(testNames), Arrays.toString(delegate.callHistory.verifyAndReturnOrder(testNames)));
	}

	@Override
	protected MockActionDelegate getDelegate() throws Throwable {
		MockActionDelegate delegate = MockWorkbenchWindowActionDelegate.lastDelegate;
		assertNotNull(delegate);
		return delegate;
