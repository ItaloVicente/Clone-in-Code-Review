	public Bug410426Test(String testName) {
		super(testName);
	}

	public void testToolbarContributionFromFactoryVisibility() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		IMenuService menus = window.getService(IMenuService.class);
		ToolBarManager manager = new ToolBarManager();

		try {
			populateTestToolbar(menus, manager);

			IContributionItem[] items = manager.getItems();
			assertEquals(6, items.length);
			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_ITEM_WITHOUT_VISIBLE_WHEN, items, true);
			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_ITEM_WITH_ALWAYS_TRUE_VISIBLE_WHEN, items, true);
			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_ITEM_WITH_ALWAYS_FALSE_VISIBLE_WHEN, items, false);

			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_MENU_MANAGER_WITHOUT_VISIBLE_WHEN, items, true);
			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_MENU_MANAGER_WITH_ALWAYS_TRUE_VISIBLE_WHEN, items, true);
			checkItem(DeclaredProgrammaticFactoryForToolbarVisibilityTest.TEST_ITEM_WITH_ALWAYS_FALSE_VISIBLE_WHEN, items, false);

			ToolBar toolBar = manager.createControl(window.getShell());
			manager.update(true);
			ToolItem[] toolItems = toolBar.getItems();
			assertEquals("Only four tool items should be created as there are four visible contributions on the six contributions:", 4, toolItems.length); //$NON-NLS-N$
		} finally {
			menus.releaseContributions(manager);
		}
	}

	private void populateTestToolbar(IMenuService menus, ToolBarManager manager) {
		menus.populateContributionManager(manager, "toolbar:org.eclipse.ui.tests.toolbarContributionFromFactoryVisibilityTest"); //$NON-NLS-N$
	}

	private void checkItem(String id, IContributionItem[] items, boolean expectedVisibility) {
		IContributionItem item = getItemWithId(id, items);

		assertNotNull(item);
		assertEquals("The contribution item with id '" + id + "' has not the expected vibility:", expectedVisibility, item.isVisible()); //$NON-NLS-N$
	}

	private IContributionItem getItemWithId(String id, IContributionItem[] items) {
		for (IContributionItem item : items) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}

	public void testNoClassCastExceptionForMenuManagerToolbarContribution() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		IMenuService menus = window.getService(IMenuService.class);
		ToolBarManager manager = new ToolBarManager();

