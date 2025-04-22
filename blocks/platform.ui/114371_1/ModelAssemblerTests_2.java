	@Test
	public void testNonUniqueImport() {
		List<MApplicationElement> imports = new ArrayList<MApplicationElement>();
		List<MApplicationElement> addedElements = new ArrayList<MApplicationElement>();

		final String windowElementId = "testImports_emptyList_window1";
		MBindingContext importContext = MCommandsFactory.INSTANCE.createBindingContext();
		importContext.setElementId(windowElementId);
		MModelFragments fragment = MFragmentFactory.INSTANCE.createModelFragments();
		fragment.getImports().add(importContext);
		imports.add(importContext);

		MBindingContext realContext = MCommandsFactory.INSTANCE.createBindingContext();
		realContext.setElementId(windowElementId);
		application.getRootContext().add(realContext);

		MTrimmedWindow realWindow1 = MBasicFactory.INSTANCE.createTrimmedWindow();
		realWindow1.setElementId(windowElementId);
		application.getChildren().add(realWindow1);

		MBindingTable table = MCommandsFactory.INSTANCE.createBindingTable();
		table.setBindingContext(importContext);
		addedElements.add(table);

		assembler.resolveImports(imports, addedElements);
		assertEquals(realContext, table.getBindingContext());
		verifyZeroInteractions(logger);
	}

