
	@Test
	public void testDynamicItem_AddOne() {
		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 0);

		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		assertTrue(tbm.getSize() == 1);
	}

	@Test
	public void testDynamicItem_AddOneBefore() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(0, toolItem2);

		assertTrue(tbm.getSize() == 2);
		assertEquals(tbm.getItems()[0].getId(), "Item2");
	}

	@Test
	public void testDynamicItem_AddMany() {
		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 0);

		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);

		List<MToolItem> itemList = Arrays.asList(toolItem1, toolItem2);
		toolBar.getChildren().addAll(itemList);

		assertTrue(tbm.getSize() == 2);
	}

	@Test
	public void testDynamicItem_RemoveOne() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 2);
		assertNotNull(toolItem1.getWidget());

		toolBar.getChildren().remove(0);

		assertTrue(tbm.getSize() == 1);
		assertEquals(tbm.getItems()[0].getId(), "Item2");

		assertNull(toolItem1.getWidget());
	}

	@Test
	public void testDynamicItem_RemoveMany() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(toolItem2);

		MToolItem toolItem3 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem3);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 3);

		List<MToolItem> itemList = Arrays.asList(toolItem1, toolItem3);
		toolBar.getChildren().removeAll(itemList);

		assertTrue(tbm.getSize() == 1);
		assertEquals(tbm.getItems()[0].getId(), "Item2");
	}

	@Test
	public void testDynamicItem_RemoveAll() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 2);

		toolBar.getChildren().clear();

		assertTrue(tbm.getSize() == 0);
	}

	@Test
	public void testDynamicItem_Move() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolItem1.setElementId("Item1");
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getManager(toolBar);

		assertTrue(tbm.getSize() == 2);
		assertEquals(tbm.getItems()[0].getId(), "Item1");
		assertEquals(tbm.getItems()[1].getId(), "Item2");

		ECollections.move(toolBar.getChildren(), 0, 1);

		assertTrue(tbm.getSize() == 2);
		assertEquals(tbm.getItems()[0].getId(), "Item2");
		assertEquals(tbm.getItems()[1].getId(), "Item1");
	}

	private ToolBarManager getManager(MToolBar toolBar) {
		return getRenderer(toolBar).getManager(toolBar);
	}

	private ToolBarManagerRenderer getRenderer(MToolBar toolbar) {
		Object renderer = toolBar.getRenderer();
		assertEquals(ToolBarManagerRenderer.class, renderer.getClass());
		return (ToolBarManagerRenderer) renderer;
	}
