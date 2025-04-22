	public void XXXtestDisposeWorkbenchWindowActionDelegateBug81422() {
		String id = MockWorkbenchWindowActionDelegate.SET_ID;
		fPage.showActionSet(id);
		MockWorkbenchWindowActionDelegate mockWWinActionDelegate = MockWorkbenchWindowActionDelegate.lastDelegate;
		Object mockAsObject = mockWWinActionDelegate;
		assertTrue(mockAsObject instanceof IActionDelegate2);
		assertTrue(mockAsObject instanceof IWorkbenchWindowActionDelegate);
		mockWWinActionDelegate.callHistory.clear();
		fPage.close();
		assertTrue(mockWWinActionDelegate.callHistory.contains("dispose"));
		assertFalse(mockWWinActionDelegate.callHistory.verifyOrder(new String[] {"dispose", "dispose"}));
	}
