	@Test
	public void testMDirectMenuItem_Check_Bug552659() {
		MWindow window = ems.createModelElement(MWindow.class);
		MMenu mainMenu = ems.createModelElement(MMenu.class);

		MMenu fileMenu = ems.createModelElement(MMenu.class);
		fileMenu.setElementId("file");
		fileMenu.setLabel("File");
		mainMenu.getChildren().add(fileMenu);

		MenuManager legacyMenuManager = new MenuManager("test.bug552659", "test.bug552659");
		MMenuItem menuItem = OpaqueElementUtil.createOpaqueMenuItem();
		menuItem.setElementId(legacyMenuManager.getId());
		OpaqueElementUtil.setOpaqueItem(menuItem, legacyMenuManager);
		fileMenu.getChildren().add(menuItem);
		window.setMainMenu(mainMenu);

		application.getChildren().add(window);
		contextRule.createAndRunWorkbench(window);

		MenuManager fileMenuManager = getRenderer(appContext, fileMenu).getManager(fileMenu);
		Menu fileMenuWidget = fileMenuManager.getMenu();

		fileMenuWidget.notifyListeners(SWT.Show, null);

		Object widget1 = menuItem.getWidget();
		assertNotNull(widget1);
		assertTrue(widget1 instanceof MenuItem);

		MenuItem menuItemWidget = (MenuItem) widget1;
		assertTrue(menuItemWidget.isEnabled());
	}

