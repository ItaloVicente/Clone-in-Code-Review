	public void testValidator() {
		assertNull(stringFieldEditor.getValidator());
		stringFieldEditor.getTextControl(shell);
		stringFieldEditor.setStringValue("bar");
		assertEquals(stringFieldEditor.getStringValue(), "bar");
		assertTrue(stringFieldEditor.isValid());

		stringFieldEditor.setValidator(x -> false);
		stringFieldEditor.setStringValue("barbar");
		assertEquals(stringFieldEditor.getStringValue(), "barbar");
		assertFalse(stringFieldEditor.isValid());
	}

