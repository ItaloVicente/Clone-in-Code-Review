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
		menuMgr.getMenu().notifyListeners(SWT.Show, new Event());
		assertTrue(filter.getCalled());
	}

	public void testDynamicMenuContribution() throws Throwable {
		testMenu(DYNAMIC_MENU_VIEW_ID);
	}

	public void testStaticMenuContribution() throws Throwable {
		testMenu(STATIC_MENU_VIEW_ID);
	}

	private void testMenu(String viewId) throws Throwable {
		ListElement red = new ListElement("red");
		ListElement blue = new ListElement("blue");
		ListElement green = new ListElement("green");
		ListElement redTrue = new ListElement("red", true);

		ListView view = (ListView) fPage.showView(viewId);
		MenuManager menuMgr = view.getMenuManager();
		view.addElement(red);
		view.addElement(blue);
		view.addElement(green);
		view.addElement(redTrue);

		ListElementActionFilter filter = ListElementActionFilter.getSingleton();

		view.selectElement(red);
		menuMgr.getMenu().notifyListeners(SWT.Show, new Event());
		assertTrue(filter.getCalled());
		assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
		assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
		assertNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
		assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
		assertNull(ActionUtil.getActionWithLabel(menuMgr, "redTrueAction_v1"));
