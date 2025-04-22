	public static void runActionUsingPath(TestCase test, IWorkbenchWindow win,
			String idPath) {
		WorkbenchWindow realWin = (WorkbenchWindow) win;
		IMenuManager mgr = realWin.getMenuBarManager();
		runActionUsingPath(test, mgr, idPath);
	}
