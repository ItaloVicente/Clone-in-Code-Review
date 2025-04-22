		super.doSetUp();
		fWindow = openTestWindow();
		fPage = fWindow.getActivePage();
	}

	public void testStaticLifecycle() throws Throwable {
		testLifecycle(STATIC_MENU_VIEW_ID);
	}

	public void testDynamicLifecycle() throws Throwable {
		testLifecycle(DYNAMIC_MENU_VIEW_ID);
	}

	private void testLifecycle(String viewId) throws Throwable {
		ListView view = (ListView) fPage.showView(viewId);

		ListElement red = new ListElement("red");
		view.addElement(red);
		view.selectElement(red);

		ListElementActionFilter filter = ListElementActionFilter.getSingleton();

		MenuManager menuMgr = view.getMenuManager();
