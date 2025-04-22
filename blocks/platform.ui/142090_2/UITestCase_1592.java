		removeWindowListener();
		doTearDown();
		fWorkbench = null;
	}

	protected void doTearDown() throws Exception {
		processEvents();
		closeAllTestWindows();
		processEvents();
	}
