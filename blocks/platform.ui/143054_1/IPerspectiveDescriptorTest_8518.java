	public void testGetLabel() {
		for (IPerspectiveDescriptor fPerspective : fPerspectives) {
			String label = fPerspective.getLabel();
			assertNotNull(label);
			assertTrue(label.length() > 0);
		}
	}
