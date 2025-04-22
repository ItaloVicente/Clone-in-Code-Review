	@Test
	public void testMenuAndItemsInFragments() {
		MWindow window = application.getChildren().get(0);
		MMenu menu0 = MMenuFactory.INSTANCE.createMenu();
		window.setMainMenu(menu0);
		menu0.setElementId("menu0");

		Set<ModelFragmentWrapper> fragmentList = new HashSet<>();
		MMenu menu00 = MMenuFactory.INSTANCE.createMenu();
		menu00.setElementId("menu00");
		MMenu menu01 = MMenuFactory.INSTANCE.createMenu();
		menu01.setElementId("menu01");
		MMenu menu02 = MMenuFactory.INSTANCE.createMenu();
		menu02.setElementId("menu02");
		MHandledMenuItem menuitem000 = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuitem000.setElementId("menuitem000");
		MHandledMenuItem menuitem001 = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuitem001.setElementId("menuitem001");
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri1"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu00";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu00), "index:1", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri2"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu01";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu01), "index:10", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri3"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu02";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu02), "index:20", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri4"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menuitem000";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu00",
					Arrays.asList(menuitem000), "index:0", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri5"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menuitem001";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu00",
					Arrays.asList(menuitem001), "index:10", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}

		assembler.processFragmentWrappers(fragmentList);

		assertEquals(3, menu0.getChildren().size());
		assertEquals(menu00, menu0.getChildren().get(0));
		assertEquals(menu01, menu0.getChildren().get(1));
		assertEquals(menu02, menu0.getChildren().get(2));
		assertEquals(2, menu00.getChildren().size());
		assertEquals(menuitem000, menu00.getChildren().get(0));
		assertEquals(menuitem001, menu00.getChildren().get(1));
	}

	@Test
	public void testMenuAndItemsInFragmentsWithImports() {
		MWindow window = application.getChildren().get(0);
		MMenu menu0 = MMenuFactory.INSTANCE.createMenu();
		window.setMainMenu(menu0);
		menu0.setElementId("menu0");

		Set<ModelFragmentWrapper> fragmentList = new HashSet<>();
		MMenu menu00 = MMenuFactory.INSTANCE.createMenu();
		menu00.setElementId("menu00");
		MMenu menu01 = MMenuFactory.INSTANCE.createMenu();
		menu01.setElementId("menu01");
		MMenu menu02 = MMenuFactory.INSTANCE.createMenu();
		menu02.setElementId("menu02");
		MHandledMenuItem menuitem000 = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuitem000.setElementId("menuitem000");
		MHandledMenuItem menuitem001 = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuitem001.setElementId("menuitem001");
		MHandledMenuItem menuitem002 = MMenuFactory.INSTANCE.createHandledMenuItem();
		menuitem002.setElementId("menuitem002");
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri1"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "fragment1";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapperMenuItem000 = createFragmentWrapper(fragmentsContainer, "children",
					"menu00", Arrays.asList(menuitem000), "index:0", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenuItem000);
			ModelFragmentWrapper fragmentWrapperMenuItem002 = createFragmentWrapper(fragmentsContainer, "children",
					"menu00", Arrays.asList(menuitem002), "index:20", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenuItem002);
			ModelFragmentWrapper fragmentWrapperMenu01 = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu01), "index:10", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenu01);

			fragmentsContainer.getImports().add(menu00);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri2"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "fragment2";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapperMenu00 = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu00), "index:1", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenu00);
			ModelFragmentWrapper fragmentWrapperMenu02 = createFragmentWrapper(fragmentsContainer, "children", "menu0",
					Arrays.asList(menu02), "index:20", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenu02);
			ModelFragmentWrapper fragmentWrapperMenuItem001 = createFragmentWrapper(fragmentsContainer, "children",
					"menu00", Arrays.asList(menuitem001), "index:3", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapperMenuItem001);
		}

		assembler.processFragmentWrappers(fragmentList);

		assertEquals(3, menu0.getChildren().size());
		assertEquals(menu00, menu0.getChildren().get(0));
		assertEquals(menu01, menu0.getChildren().get(1));
		assertEquals(menu02, menu0.getChildren().get(2));
		assertEquals(3, menu00.getChildren().size());
		assertEquals(menuitem000, menu00.getChildren().get(0));
		assertEquals(menuitem001, menu00.getChildren().get(1));
		assertEquals(menuitem002, menu00.getChildren().get(2));
	}

	@Test
	public void testMenuAndItemsInFragments2() {
		MWindow window = application.getChildren().get(0);
		MMenu menu = MMenuFactory.INSTANCE.createMenu();
		window.setMainMenu(menu);
		menu.setElementId("menu");

		Set<ModelFragmentWrapper> fragmentList = new HashSet<>();
		MMenu menu1 = MMenuFactory.INSTANCE.createMenu();
		menu1.setElementId("menu1");
		MMenu menu2 = MMenuFactory.INSTANCE.createMenu();
		menu2.setElementId("menu2");
		MMenu menu3 = MMenuFactory.INSTANCE.createMenu();
		menu3.setElementId("menu3");
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri3"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu3";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu2",
					Arrays.asList(menu3), "index:0", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri1"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu1";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu",
					Arrays.asList(menu1), "index:10", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri2"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu2";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu1",
					Arrays.asList(menu2), "index:20", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}

		assembler.processFragmentWrappers(fragmentList);

		assertEquals(1, menu.getChildren().size());
		assertEquals(menu1, menu.getChildren().get(0));
		assertEquals(menu2, menu1.getChildren().get(0));
		assertEquals(menu3, menu2.getChildren().get(0));
	}

	@Test
	public void testMenuAndItemsInFragments3() {
		MWindow window = application.getChildren().get(0);
		MMenu menu = MMenuFactory.INSTANCE.createMenu();
		window.setMainMenu(menu);
		menu.setElementId("menu");

		Set<ModelFragmentWrapper> fragmentList = new HashSet<>();
		MMenu menu1 = MMenuFactory.INSTANCE.createMenu();
		menu1.setElementId("menu1");
		MMenu menu2 = MMenuFactory.INSTANCE.createMenu();
		menu2.setElementId("menu2");
		MMenu menu3 = MMenuFactory.INSTANCE.createMenu();
		menu3.setElementId("menu3");
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri1"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu1";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu",
					Arrays.asList(menu1), "index:10", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri3"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu3";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu2",
					Arrays.asList(menu3), "index:0", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}
		{
			Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri2"));
			resourceSet.getResources().add(fragmentResource);
			final String contributorName = "menu2";
			final String contributorURI = fragmentResource.getURI().toString();

			MModelFragments fragmentsContainer = MFragmentFactory.INSTANCE.createModelFragments();
			fragmentResource.getContents().add((EObject) fragmentsContainer);

			ModelFragmentWrapper fragmentWrapper = createFragmentWrapper(fragmentsContainer, "children", "menu1",
					Arrays.asList(menu2), "index:20", contributorName, contributorURI, false);
			fragmentList.add(fragmentWrapper);
		}

		assembler.processFragmentWrappers(fragmentList);

		assertEquals(1, menu.getChildren().size());
		assertEquals(menu1, menu.getChildren().get(0));
		assertEquals(menu2, menu1.getChildren().get(0));
		assertEquals(menu3, menu2.getChildren().get(0));
	}

	@Test
	public void testModelFragmentComparatorWithCorrectlySortedList() {
		MHandledToolItem x = MMenuFactory.INSTANCE.createHandledToolItem();
		x.setElementId("x");
		toolBar.getChildren().add(x);
		MHandledToolItem y = MMenuFactory.INSTANCE.createHandledToolItem();
		y.setElementId("y");
		toolBar.getChildren().add(y);
		MHandledToolItem z = MMenuFactory.INSTANCE.createHandledToolItem();
		z.setElementId("z");
		toolBar.getChildren().add(z);

		Resource fragmentResource = factory.createResource(URI.createURI("fragmentvirtualuri"));
		resourceSet.getResources().add(fragmentResource);
		final String contributorName = "testAfterFragmentReference3";
		final String contributorURI = fragmentResource.getURI().toString();

		MModelFragments fragmentsContainerA = MFragmentFactory.INSTANCE.createModelFragments();
		fragmentResource.getContents().add((EObject) fragmentsContainerA);
		MHandledToolItem a = MMenuFactory.INSTANCE.createHandledToolItem();
		a.setElementId("a");
		MModelFragments fragmentsContainerB = MFragmentFactory.INSTANCE.createModelFragments();
		fragmentResource.getContents().add((EObject) fragmentsContainerB);
		MHandledToolItem b = MMenuFactory.INSTANCE.createHandledToolItem();
		b.setElementId("b");
		MModelFragments fragmentsContainerC = MFragmentFactory.INSTANCE.createModelFragments();
		fragmentResource.getContents().add((EObject) fragmentsContainerC);
		MHandledToolItem c = MMenuFactory.INSTANCE.createHandledToolItem();
		c.setElementId("c");

		ModelFragmentWrapper fragmentWrapper1 = createFragmentWrapper(fragmentsContainerA, "children", MAIN_TOOLBAR_ID,
				Arrays.asList(a), "after:b", contributorName, contributorURI, false);
		ModelFragmentWrapper fragmentWrapper2 = createFragmentWrapper(fragmentsContainerB, "children", MAIN_TOOLBAR_ID,
				Arrays.asList(b), "after:c", contributorName, contributorURI, false);
		ModelFragmentWrapper fragmentWrapper3 = createFragmentWrapper(fragmentsContainerC, "children", MAIN_TOOLBAR_ID,
				Arrays.asList(c), "after:y", contributorName, contributorURI, false);

		Set<ModelFragmentWrapper> fragmentList = new TreeSet<>(new ModelFragmentComparator(application));
		fragmentList.addAll(Arrays.asList(fragmentWrapper1, fragmentWrapper2, fragmentWrapper3));

		{
			Iterator<ModelFragmentWrapper> iterator = fragmentList.iterator();
			assertEquals(fragmentWrapper3, iterator.next());
			assertEquals(fragmentWrapper2, iterator.next());
			assertEquals(fragmentWrapper1, iterator.next());
		}

		Set<ModelFragmentWrapper> fragmentList2 = new TreeSet<>(new ModelFragmentComparator(application));
		fragmentList2.addAll(fragmentList);
