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
