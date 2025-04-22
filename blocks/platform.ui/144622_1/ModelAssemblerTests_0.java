	@Test
	public void testNotExistsNonInitialFragmentApply() throws IOException {

		IExtension[] createExtensions = createExtensions(
				"org.eclipse.e4.ui.tests/data/ModelAssembler/fragment_notexists.xml");
		MWindow mainWindow = modelService.createModelElement(MWindow.class);
		mainWindow.setElementId("mainWindow");

		MPartSashContainer partSashContainer = modelService.createModelElement(MPartSashContainer.class);
		MPartStack partStackTop = modelService.createModelElement(MPartStack.class);
		partStackTop.setElementId("partStackTop");
		MPartStack partStackBottom = modelService.createModelElement(MPartStack.class);
		partStackBottom.setElementId("partStackBottom");

		MPart part1 = modelService.createModelElement(MPart.class);
		part1.setElementId("part1Id");
		MPart part2 = modelService.createModelElement(MPart.class);
		part2.setElementId("part2Id");
		part2.getPersistedState().put("id", "kgu");

		partStackTop.getChildren().add(part1);
		partStackTop.getChildren().add(part2);

		partSashContainer.getChildren().add(partStackTop);
		partSashContainer.getChildren().add(partStackBottom);

		mainWindow.getChildren().add(partSashContainer);
		application.getChildren().add(mainWindow);

		EObject ePart2 = (EObject) part2;
		E4XMIResource r = (E4XMIResource) ePart2.eResource();
		r.setID(ePart2, "_gXKowH1YEemug7TLPAdneg");

		assembler.processFragments(createExtensions, false);

		List<MPart> findElements = modelService.findElements(application, "part2Id", MPart.class);
		assertThat(findElements, hasSize(1));
		MPart mPart = findElements.get(0);
		assertThat(mPart.getPersistedState(), hasEntry("id", "kgu"));

		List<MPart> findPart3 = modelService.findElements(application, "part3Id", MPart.class);
		assertThat(findPart3, hasSize(1));

	}

	@Test
	public void testNotExistsNonInitialFragmentPartlyMerge() throws IOException {

		IExtension[] createExtensions = createExtensions(
				"org.eclipse.e4.ui.tests/data/ModelAssembler/fragment_notexists_partlyMerge.xml");
		MTrimmedWindow mainTrimmedWindow = modelService.createModelElement(MTrimmedWindow.class);
		mainTrimmedWindow.setElementId("mainWindow");

		MTrimBar trimBar = modelService.createModelElement(MTrimBar.class);
		trimBar.setElementId("TestApp.trimbar.top");

		MToolBar toolBar = modelService.createModelElement(MToolBar.class);
		toolBar.setElementId("org.eclipse.ui.main.toolbar");

		trimBar.getChildren().add(toolBar);
		mainTrimmedWindow.getTrimBars().add(trimBar);

		MCommand commandOpen = modelService.createModelElement(MCommand.class);
		commandOpen.setElementId("command.open");

		MHandler handlerOpen = modelService.createModelElement(MHandler.class);
		handlerOpen.setCommand(commandOpen);
		handlerOpen.setElementId("handler.open");

		application.getCommands().add(commandOpen);
		application.getHandlers().add(handlerOpen);
		application.getChildren().add(mainTrimmedWindow);


		assembler.processFragments(createExtensions, false);

		List<MHandledToolItem> foundElements = modelService.findElements(application, "handledtoolitem.open",
				MHandledToolItem.class);
		assertThat(foundElements, hasSize(1));
		MHandledToolItem mMenuItem = foundElements.get(0);
		assertThat(mMenuItem.getCommand().getElementId(), equalTo(commandOpen.getElementId()));
		assertThat(mMenuItem.getElementId(), equalTo("handledtoolitem.open"));
	}

	@Test
	public void testInitialApplyButNonInitialStartupFragment() throws IOException {
		IExtension[] createExtensions = createExtensions(
				"org.eclipse.e4.ui.tests/data/ModelAssembler/fragment_initial.xml");
		MWindow mainWindow = modelService.createModelElement(MWindow.class);
		mainWindow.setElementId("mainWindow");

		MPartSashContainer partSashContainer = modelService.createModelElement(MPartSashContainer.class);
		MPartStack partStackTop = modelService.createModelElement(MPartStack.class);
		partStackTop.setElementId("partStackTop");
		MPartStack partStackBottom = modelService.createModelElement(MPartStack.class);
		partStackBottom.setElementId("partStackBottom");

		MPart part1 = modelService.createModelElement(MPart.class);
		part1.setElementId("part1Id");
		MPart part2 = modelService.createModelElement(MPart.class);
		part2.setElementId("part2Id");

		partStackTop.getChildren().add(part1);
		partStackTop.getChildren().add(part2);

		partSashContainer.getChildren().add(partStackTop);
		partSashContainer.getChildren().add(partStackBottom);

		mainWindow.getChildren().add(partSashContainer);
		application.getChildren().add(mainWindow);

		assembler.processFragments(createExtensions, false);

		List<MPart> findElements = modelService.findElements(application, "part2Id", MPart.class);

		assertThat(findElements, hasSize(1));
	}

