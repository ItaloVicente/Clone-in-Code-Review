	@Override
	protected void showMenu(MenuManager mgr) throws Throwable {
		WorkbenchWindow win = (WorkbenchWindow) fWindow;
		((MenuManager) win.getMenuBarManager()).getMenu().notifyListeners(SWT.Show, new Event());
	}

