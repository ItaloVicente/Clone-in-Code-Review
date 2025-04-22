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
