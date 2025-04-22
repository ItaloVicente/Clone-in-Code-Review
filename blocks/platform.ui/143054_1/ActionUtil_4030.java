	public static void runActionWithLabel(TestCase test, IWorkbenchWindow win,
			String label) {
		WorkbenchWindow realWin = (WorkbenchWindow) win;
		IMenuManager mgr = realWin.getMenuBarManager();
		runActionWithLabel(test, mgr, label);
	}
