	public void testGetId() {
		for (IPerspectiveDescriptor fPerspective : fPerspectives) {
			String id = fPerspective.getId();
			assertNotNull(id);
			assertTrue(id.length() > 0);
		}
	}
