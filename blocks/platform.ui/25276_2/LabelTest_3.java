	public void testTransparent() throws Exception {
		Label labelToTest = createTestLabel("Label { background-color: transparent; }");

		Method drawsBackgroundMethod = labelToTest.getClass()
				.getDeclaredMethod("drawsBackground");
		drawsBackgroundMethod.setAccessible(true);
		Boolean result = (Boolean) drawsBackgroundMethod.invoke(labelToTest);
		assertFalse(result);
	}

	public void testTransparentProperty() throws Exception {
		Label labelToTest = createTestLabel("Label { color: red; }");
		labelToTest.setBackground(null);
		labelToTest.setBackgroundImage(null);

		String value = engine.retrieveCSSProperty(
				engine.getElement(labelToTest), "background-color", null);
		assertEquals("transparent", value);
	}

