	@Test
	public void testMDirectMenuItem_Check_Bug547050() {
		MWindow window = ems.createModelElement(MWindow.class);
		MMenu mainMenu = ems.createModelElement(MMenu.class);

		MMenu fileMenu = ems.createModelElement(MMenu.class);
		fileMenu.setElementId("file");
		fileMenu.setLabel("File");
		mainMenu.getChildren().add(fileMenu);

		MMenuItem menuItem = ems.createModelElement(MDirectMenuItem.class);
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
		assertFalse(menuItemWidget.isEnabled());
	}

