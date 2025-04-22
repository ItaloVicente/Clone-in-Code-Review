	public void testTransparent() throws Exception {
		Button buttonToTest = createTestButton("Button { background-color: transparent; }");

		Method drawsBackgroundMethod = buttonToTest.getClass()
				.getDeclaredMethod("drawsBackground");
		drawsBackgroundMethod.setAccessible(true);
		Boolean result = (Boolean) drawsBackgroundMethod.invoke(buttonToTest);
		assertFalse(result);
	}

	public void testTransparentProperty() throws Exception {
		Button buttonToTest = createTestButton("Button { background-color: transparent; }");
		buttonToTest.setBackground(null);
		buttonToTest.setBackgroundImage(null);

		String value = engine.retrieveCSSProperty(
				engine.getElement(buttonToTest), "background-color", null);
		assertEquals("transparent", value);
	}

