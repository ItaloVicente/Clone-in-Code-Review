	}

	public IWorkbenchPage openTestPage(IWorkbenchWindow win) {
		IWorkbenchPage[] pages = openTestPage(win, 1);
		if (pages != null) {
			return pages[0];
		}
		return null;
	}

	public IWorkbenchPage[] openTestPage(IWorkbenchWindow win, int pageTotal) {
		try {
			IWorkbenchPage[] pages = new IWorkbenchPage[pageTotal];
			IAdaptable input = getPageInput();

			for (int i = 0; i < pageTotal; i++) {
				pages[i] = win.openPage(EmptyPerspective.PERSP_ID, input);
			}
			return pages;
		} catch (WorkbenchException e) {
			fail("Problem opening test page", e);
			return null;
		}
	}

	public void closeAllPages(IWorkbenchWindow window) {
		IWorkbenchPage[] pages = window.getPages();
		for (IWorkbenchPage page : pages) {
			page.close();
		}
	}

	protected void manageWindows(boolean manage) {
		windowListener.setEnabled(manage);
	}

	protected IWorkbench getWorkbench() {
		return fWorkbench;
	}
