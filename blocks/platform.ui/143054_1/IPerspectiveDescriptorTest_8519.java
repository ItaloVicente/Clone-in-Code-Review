	public void testGetImageDescriptor() {
		for (IPerspectiveDescriptor fPerspective : fPerspectives) {
			ImageDescriptor image = fPerspective.getImageDescriptor();
			assertNotNull(image);
		}
	}
