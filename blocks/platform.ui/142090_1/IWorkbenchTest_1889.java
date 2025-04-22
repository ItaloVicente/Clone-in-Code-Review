		}

		boolean exceptionOccured = false;
		try {
			win = fWorkbench.openWorkbenchWindow("afdasfdasf", getPageInput());
		} catch (WorkbenchException ex) {
			exceptionOccured = true;
		}

		assertEquals(exceptionOccured, true);
	}

	public void XXXtestOpenWorkbenchWindow2() throws Throwable {
		IWorkbenchWindow win = null;

		try {
			win = fWorkbench
					.openWorkbenchWindow(getPageInput());
			assertNotNull(win);
