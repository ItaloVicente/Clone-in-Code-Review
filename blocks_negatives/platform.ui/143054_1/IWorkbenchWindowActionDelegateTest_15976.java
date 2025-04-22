    /**
     * Regression test for bug 81422.  Tests to ensure that dispose() is only
     * called once if the delegate implements both IWorkbenchWindowActionDelegate
     * and IActionDelegate2.
     */
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
