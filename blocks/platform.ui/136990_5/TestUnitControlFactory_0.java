		assertTrue(label.getLayoutData() instanceof GridData);
	}
	
	@Test
	public void testUniqueLayoutData() {
		GridDataFactory gridDataFactory = GridDataFactory.fillDefaults().grab(true, false);
		TestFactory factory = TestFactory.newTest().tooltip("toolTip").enabled(false).layoutData(gridDataFactory::create);
		
		Label label = factory.create(shell);
		Label label2 = factory.create(shell);
		
		assertNotEquals(label.getLayoutData(), label2.getLayoutData());
