	@Test
	public void testExpandableComposite_foregroundColorGetsReset_foregroundCollorIsNull() throws Exception {
		ExpandableComposite compositeToTest = createTestExpandableComposite(
				"ExpandableComposite { titlebar-color: #FF0000; }");
		assertNotNull(compositeToTest.getTitleBarForeground());
		assertEquals(RED, compositeToTest.getTitleBarForeground().getRGB());

		engine.reset();

		assertNull(compositeToTest.getTitleBarForeground());
	}

