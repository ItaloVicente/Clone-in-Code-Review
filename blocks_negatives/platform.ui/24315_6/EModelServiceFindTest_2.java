		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		window.setElementId("singleValidId");
		app.getChildren().add(window);

		MMenu mainMenu = MenuFactoryImpl.eINSTANCE.createMenu();
		window.setMainMenu(mainMenu);

		MMenu mainMenuItem = MenuFactoryImpl.eINSTANCE.createMenu();
		mainMenu.getChildren().add(mainMenuItem);

		MPartSashContainer psc = BasicFactoryImpl.eINSTANCE
				.createPartSashContainer();
		psc.setElementId("twoValidIds");
		psc.getTags().add("oneValidTag");
		window.getChildren().add(psc);

		MPartStack stack = BasicFactoryImpl.eINSTANCE.createPartStack();
		stack.getTags().add("twoValidTags");
		psc.getChildren().add(stack);

		MPart part1 = BasicFactoryImpl.eINSTANCE.createPart();
		part1.setElementId("twoValidIds");
		stack.getChildren().add(part1);

		MPart part2 = BasicFactoryImpl.eINSTANCE.createPart();
		part2.getTags().add("twoValidTags");
		part2.getTags().add("secondTag");
		stack.getChildren().add(part2);

		MPart part3 = BasicFactoryImpl.eINSTANCE.createPart();
		psc.getChildren().add(part3);

		MMenu menu = MenuFactoryImpl.eINSTANCE.createMenu();
		menu.setElementId("menuId");
		part1.getMenus().add(menu);

		MMenu menuItem1 = MenuFactoryImpl.eINSTANCE.createMenu();
		menuItem1.setElementId("menuItem1Id");
		menu.getChildren().add(menuItem1);

		MMenu menuItem2 = MenuFactoryImpl.eINSTANCE.createMenu();
		menuItem2.setElementId("menuItem2Id");
		menu.getChildren().add(menuItem2);

		MToolBar toolBar = MenuFactoryImpl.eINSTANCE.createToolBar();
		toolBar.setElementId("toolBarId");
		part2.setToolbar(toolBar);

		MToolControl toolControl1 = MenuFactoryImpl.eINSTANCE
				.createToolControl();
		toolControl1.setElementId("toolControl1Id");
		toolBar.getChildren().add(toolControl1);

		MToolControl toolControl2 = MenuFactoryImpl.eINSTANCE
				.createToolControl();
		toolControl2.setElementId("toolControl2Id");
		toolBar.getChildren().add(toolControl2);
