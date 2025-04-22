
		ToggleHyperlink toggle = (ToggleHyperlink) compositeToTest.getChildren()[0];
		assertNotNull(toggle.getDecorationColor());
		assertEquals(RED, toggle.getDecorationColor().getRGB());
		assertNotNull(toggle.getHoverDecorationColor());
		assertEquals(GREEN, toggle.getHoverDecorationColor().getRGB());
