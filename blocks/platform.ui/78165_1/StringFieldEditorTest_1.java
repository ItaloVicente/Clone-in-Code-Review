	public void testValidator() {
		Text text = stringFieldEditor.getTextControl(shell);
		text.setText("bar");
		assertEquals(stringFieldEditor.getStringValue(), "bar");
		assertTrue(stringFieldEditor.isValid());

		stringFieldEditor.setValidator(x -> false);
		stringFieldEditor.setStringValue("barbar");
		assertEquals(stringFieldEditor.getStringValue(), "barbar");
		assertFalse(stringFieldEditor.isValid());
	}

