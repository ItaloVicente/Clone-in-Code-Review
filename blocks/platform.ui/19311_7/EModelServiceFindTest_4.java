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

