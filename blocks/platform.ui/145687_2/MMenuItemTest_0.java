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

		MenuManagerRenderer renderer = getRenderer(appContext, mainMenu);
		MenuManager manager = renderer.getManager(mainMenu);
		assertNotNull("failed to create menu bar manager", manager);
		manager.updateAll(true);

		assertEquals(1, manager.getSize());
		MenuManager fileManager = renderer.getManager(fileMenu);

		MenuManagerRendererFilter.updateElementVisibility(fileMenu, renderer, fileManager, appContext, 2, true);

		Object widget1 = menuItem.getWidget();
		assertNotNull(widget1);
		assertTrue(widget1 instanceof MenuItem);

		MenuItem menuItemWidget = (MenuItem) widget1;
		assertFalse(menuItemWidget.isEnabled());
	}

