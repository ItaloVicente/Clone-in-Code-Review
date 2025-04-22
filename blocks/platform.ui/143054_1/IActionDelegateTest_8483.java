		runAction(obj);
		MockActionDelegate delegate = getDelegate();
		assertNotNull(delegate);
		assertTrue(delegate.callHistory.verifyOrder(new String[] {
				"selectionChanged", "run" }));
	}
