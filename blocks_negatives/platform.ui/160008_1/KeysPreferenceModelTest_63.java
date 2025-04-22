	private void assertChangeEvent(int eventNum, PropertyChangeEvent expected,
			PropertyChangeEvent event) {
		assertEquals("source: " + eventNum, expected.getSource(), event
				.getSource());
		assertEquals("property: " + eventNum, expected.getProperty(), event
				.getProperty());
		assertEquals("old: " + eventNum, expected.getOldValue(), event
				.getOldValue());
		assertEquals("new: " + eventNum, expected.getNewValue(), event
				.getNewValue());
