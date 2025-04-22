
	@Test
	public void testDynamicItem_AddOne() {
		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(0, tbm.getSize());

		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		assertEquals(1, tbm.getSize());
	}

	@Test
	public void testDynamicItem_AddOneBefore() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(tbm.getSize(), 1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(0, toolItem2);

		assertEquals(2, tbm.getSize());
		assertEquals("Item2", tbm.getItems()[0].getId());
	}

	@Test
	public void testDynamicItem_AddMany() {
		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(0, tbm.getSize());

		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);

		List<MToolItem> itemList = Arrays.asList(toolItem1, toolItem2);
		toolBar.getChildren().addAll(itemList);

		assertEquals(2, tbm.getSize());
	}

	@Test
	public void testDynamicItem_RemoveOne() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolItem2.setElementId("Item2");
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(2, tbm.getSize());
		assertNotNull(toolItem1.getWidget());

		toolBar.getChildren().remove(0);

		assertEquals(1, tbm.getSize(), 1);
		assertEquals("Item2", tbm.getItems()[0].getId());

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
		ToolBarManager tbm = getToolBarManager();

		assertEquals(3, tbm.getSize());

		List<MToolItem> itemList = Arrays.asList(toolItem1, toolItem3);
		toolBar.getChildren().removeAll(itemList);

		assertEquals(1, tbm.getSize());
		assertEquals("Item2", tbm.getItems()[0].getId());
	}

	@Test
	public void testDynamicItem_RemoveAll() {
		MToolItem toolItem1 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem1);

		MToolItem toolItem2 = ems.createModelElement(MDirectToolItem.class);
		toolBar.getChildren().add(toolItem2);

		contextRule.createAndRunWorkbench(window);
		ToolBarManager tbm = getToolBarManager();

		assertEquals(2, tbm.getSize());

		toolBar.getChildren().clear();

		assertEquals(0, tbm.getSize());
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
		ToolBarManager tbm = getToolBarManager();

		assertEquals(2, tbm.getSize(), 2);
		assertEquals("Item1", tbm.getItems()[0].getId());
		assertEquals("Item2", tbm.getItems()[1].getId());

		ECollections.move(toolBar.getChildren(), 0, 1);

		assertEquals(2, tbm.getSize(), 2);
		assertEquals("Item2", tbm.getItems()[0].getId());
		assertEquals("Item1", tbm.getItems()[1].getId());
	}

	private ToolBarManager getToolBarManager() {
		Object renderer = toolBar.getRenderer();
		assertEquals(ToolBarManagerRenderer.class, renderer.getClass());
		return ((ToolBarManagerRenderer) renderer).getManager(toolBar);
	}

