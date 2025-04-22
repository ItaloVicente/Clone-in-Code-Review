	@Test
	public void testVisibilityOfMenuItemChangesBasedOnImperativeExpression() {
		MWindow window = ems.createModelElement(MWindow.class);
		MMenu mainMenu = ems.createModelElement(MMenu.class);
		mainMenu.setElementId("org.eclipse.ui.main.menu");
		window.setMainMenu(mainMenu);

		MMenu fileMenu = ems.createModelElement(MMenu.class);
		fileMenu.setElementId("file");
		fileMenu.setLabel("File");
		mainMenu.getChildren().add(fileMenu);

		MMenuItem item1 = ems.createModelElement(MDirectMenuItem.class);
		item1.setElementId("item1");
		item1.setLabel("item1");
		fileMenu.getChildren().add(item1);

		MMenuSeparator sep = ems.createModelElement(MMenuSeparator.class);
		sep.setElementId("group1");
		fileMenu.getChildren().add(sep);

		MMenuItem item2 = ems.createModelElement(MDirectMenuItem.class);
		item2.setElementId("item2");
		item2.setLabel("item2");
		fileMenu.getChildren().add(item2);

		MApplication application = ems.createModelElement(MApplication.class);
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class, application);
		createMenuContributionWithImperativeExpression(application);

		wb = new E4Workbench(window, appContext);
		wb.createAndRunUI(window);

		MenuManagerRenderer renderer = getRenderer(appContext, mainMenu);
		MenuManager manager = renderer.getManager(mainMenu);
		manager.updateAll(true);

		assertEquals(2, manager.getSize());

		MenuManager vanishManager = (MenuManager) manager.getItems()[1];
		assertEquals("vanish", vanishManager.getId());
		assertFalse(vanishManager.isVisible());
		assertNull(vanishManager.getMenu());

		appContext.set("mmc1", Boolean.TRUE);

		assertTrue(vanishManager.isVisible());
		assertNotNull(vanishManager.getMenu());

		appContext.remove("mmc1");

		assertFalse(vanishManager.isVisible());
		Menu vanishMenu = vanishManager.getMenu();
		if (vanishMenu != null) {
			assertTrue(vanishMenu.isDisposed());
		}

		appContext.set("mmc1", Boolean.TRUE);

		assertTrue(vanishManager.isVisible());
		assertNotNull(vanishManager.getMenu());
		assertFalse(vanishManager.getMenu().isDisposed());
	}

