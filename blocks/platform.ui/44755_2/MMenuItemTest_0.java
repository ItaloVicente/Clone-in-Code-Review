	public void testMHandledMenuItem_Check_Bug463280() {
		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		MMenu menu = MenuFactoryImpl.eINSTANCE.createMenu();
		MHandledMenuItem menuItem = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
		MCommand command = CommandsFactoryImpl.eINSTANCE.createCommand();

		command.setElementId("commandId");

		menuItem.setCommand(command);
		menuItem.setType(ItemType.CHECK);
		menuItem.setSelected(true);
		menuItem.setLabel("&Test Xxx");
		menuItem.setMnemonics("");

		menu.getChildren().add(menuItem);
		window.setMainMenu(menu);

		MApplication application = ApplicationFactoryImpl.eINSTANCE.createApplication();
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class.getName(), application);

		wb = new E4Workbench(window, appContext);
		wb.createAndRunUI(window);

		MenuManager barManager = (MenuManager) ((Menu) menu.getWidget()).getData();
		barManager.updateAll(true);

		Object widget1 = menuItem.getWidget();
		assertNotNull(widget1);
		assertTrue(widget1 instanceof MenuItem);

		MenuItem menuItemWidget = (MenuItem) widget1;
		assertFalse(menuItemWidget.getText().startsWith("&&"));
	}

